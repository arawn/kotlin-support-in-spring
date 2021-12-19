package com.daangn;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsynchronousProgrammingInJava {

    public static void main(String[] args) throws Exception {
        var username = "arawn";
        var accessToken = "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";

        var async = getGithubInfoAsync(username, accessToken).get(3, TimeUnit.SECONDS);
        System.out.println(async.user);
        System.out.println(async.organizations);
        System.out.println(async.repositories);

        var reactive = getGithubInfoReactive(username, accessToken).block(Duration.ofSeconds(3));
        System.out.println(reactive.user);
        System.out.println(reactive.organizations);
        System.out.println(reactive.repositories);
    }

    static CompletableFuture<GithubInfo> getGithubInfoAsync(String username, String accessToken) {
        var operations = new GithubOperations(accessToken);

        return operations.fetchUserAsync(username).thenCompose(user -> {
            var organizationsFuture = operations.fetchOrganizationsAsync(user);
            var repositoriesFuture = operations.fetchRepositoriesAsync(user);
            return organizationsFuture.thenCombine(
                repositoriesFuture,
                (organizations, repositories) -> new GithubInfo(user, organizations, repositories)
            );
        });
    }

    static Mono<GithubInfo> getGithubInfoReactive(String username, String accessToken) {
        var operations = new GithubOperations(accessToken);

        return operations.fetchUserReactive(username).flatMap(user -> {
            var organizationsFuture = operations.fetchOrganizationsReactive(user);
            var repositoriesFuture = operations.fetchRepositoriesReactive(user);
            return organizationsFuture.zipWith(
                    repositoriesFuture,
                    (organizations, repositories) -> new GithubInfo(user, organizations, repositories)
            );
        });
    }

    public static class GithubInfo {

        final GithubOperations.User user;
        final List<GithubOperations.Organization> organizations;
        final List<GithubOperations.Repository> repositories;

        GithubInfo(GithubOperations.User user, List<GithubOperations.Organization> organizations, List<GithubOperations.Repository> repositories) {
            this.user = user;
            this.organizations = organizations;
            this.repositories = repositories;
        }

    }

}
