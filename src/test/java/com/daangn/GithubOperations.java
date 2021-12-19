package com.daangn;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GithubOperations {

    private final Executor executor;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    private final String accessToken;

    public GithubOperations(String accessToken) {
        this.executor = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("go-"));
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .executor(executor)
                .build();
        this.objectMapper = new ObjectMapper(); {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        this.logger = LoggerFactory.getLogger(getClass());

        this.accessToken = "Basic " + Base64.getEncoder().encodeToString((accessToken + ":x-oauth-basic").getBytes());
    }

    public CompletableFuture<User> fetchUserAsync(String username) {
        logger.debug("fetching user: {}", username);

        try {
            return httpClient.sendAsync(
                    HttpRequest.newBuilder()
                            .uri(buildUri(String.format("users/%s", username)))
                            .header("Authorization", accessToken)
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofByteArray()
            ).thenApplyAsync(response -> {
                logger.debug("fetched user. try to transform data.");

                try {
                    return objectMapper.readValue(response.body(), User.class);
                } catch (Exception error) {
                    throw new IllegalStateException(error);
                }
            }, executor);
        } catch (Exception error) {
            throw new IllegalStateException(error);
        }
    }

    public Mono<User> fetchUserReactive(String username) {
        return Mono.fromCompletionStage(fetchUserAsync(username));
    }

    public CompletableFuture<List<Organization>> fetchOrganizationsAsync(User user) {
        logger.debug("fetching organizations: {}", user.login);

        try {
            return httpClient.sendAsync(
                    HttpRequest.newBuilder()
                            .uri(buildUri(String.format("users/%s/orgs", user.login)))
                            .header("Authorization", accessToken)
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofByteArray()
            ).thenApplyAsync(response -> {
                logger.debug("fetched organizations. try to transform data.");

                try {
                    JavaType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Organization.class);
                    return objectMapper.readValue(response.body(), type);
                } catch (Exception error) {
                    throw new IllegalStateException(error);
                }
            }, executor);
        } catch (Exception error) {
            throw new IllegalStateException(error);
        }
    }

    public Mono<List<Organization>> fetchOrganizationsReactive(User user) {
        return Mono.fromCompletionStage(fetchOrganizationsAsync(user));
    }

    public CompletableFuture<List<Repository>> fetchRepositoriesAsync(User user) {
        logger.debug("fetching repositories: {}", user.login);

        try {
            return httpClient.sendAsync(
                    HttpRequest.newBuilder()
                            .uri(buildUri(String.format("users/%s/repos", user.login)))
                            .header("Authorization", accessToken)
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofByteArray()
            ).thenApplyAsync(response -> {
                logger.debug("fetched repositories. try to transform data.");

                try {
                    JavaType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Repository.class);
                    return objectMapper.readValue(response.body(), type);
                } catch (Exception error) {
                    throw new IllegalStateException(error);
                }
            }, executor);
        } catch (Exception error) {
            throw new IllegalStateException(error);
        }
    }

    public Mono<List<Repository>> fetchRepositoriesReactive(User user) {
        return Mono.fromCompletionStage(fetchRepositoriesAsync(user));
    }

    private URI buildUri(String path) {
        try {
            return new URI(String.format("https://api.github.com/%s", path));
        } catch (URISyntaxException error) {
            throw new IllegalArgumentException(error);
        }
    }

    public static class User {

        private String login;
        private String name;
        private String email;

        public String getLogin() {
            return login;
        }

        private void setLogin(String login) {
            this.login = login;
        }

        public String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        private void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{" +
                    "login='" + login + '\'' +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public static class Organization {

        private String login;
        private String description;

        public String getLogin() {
            return login;
        }

        private void setLogin(String login) {
            this.login = login;
        }

        public String getDescription() {
            return description;
        }

        private void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Organization{" +
                    "login='" + login + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

    }

    public static class Repository {

        private String name;
        private String description;

        public String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        private void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Repository{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

    }

}
