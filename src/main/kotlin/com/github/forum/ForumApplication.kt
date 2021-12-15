package com.github.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ForumApplication {

    companion object {
        const val PROFILE_LOCAL = "local"
        const val PROFILE_DEVELOPMENT = "dev"
        const val PROFILE_PRODUCTION = "prod"
    }
}

fun main(args: Array<String>) {
    runApplication<ForumApplication>(*args)
}
