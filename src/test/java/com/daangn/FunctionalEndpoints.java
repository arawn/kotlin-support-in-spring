package com.daangn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootConfiguration
@ImportAutoConfiguration({
    DispatcherServletAutoConfiguration.class,
    ServletWebServerFactoryAutoConfiguration.class,
    HttpEncodingAutoConfiguration.class,
    WebMvcAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class
})
public class FunctionalEndpoints {

    @Bean
    public PersonHandler personHandler() {
        return new PersonHandler();
    }

    @Bean
    public RouterFunction<ServerResponse> personRouter(PersonHandler handler) {
        return route().path("/person", builder ->
            builder.nest(accept(APPLICATION_JSON), nestBuilder ->
                nestBuilder.GET("/{id}", handler::getPerson)
                           .GET(handler::listPeople)
            ).POST(handler::createPerson)
        ).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(FunctionalEndpoints.class, args);
    }

    @Controller
    @RequestMapping("/person")
    static class PersonController {

        @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
        public Object getPerson() {
            return Collections.emptyMap();
        }

        @GetMapping(produces = APPLICATION_JSON_VALUE)
        public List<?> listPerson() {
            return Collections.emptyList();
        }

        @PostMapping
        public void createPerson() {

        }

    }

}
