package com.daangn

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.router

@SpringBootConfiguration
@ImportAutoConfiguration(
    DispatcherServletAutoConfiguration::class,
    ServletWebServerFactoryAutoConfiguration::class,
    HttpEncodingAutoConfiguration::class,
    WebMvcAutoConfiguration::class,
    HttpMessageConvertersAutoConfiguration::class
)
class RouterDslConfiguration {

    @Bean
    fun personRouter(personHandler: PersonHandler) = router {
        "/person".nest {
            accept(APPLICATION_JSON).nest {
                GET("/{id}", personHandler::getPerson)
                GET(personHandler::listPeople)
            }
            POST(personHandler::createPerson)
        }
    }

    @Bean
    fun personHandler() = PersonHandler()
}

fun main(args: Array<String>) {
    runApplication<RouterDslConfiguration>(*args)
}
