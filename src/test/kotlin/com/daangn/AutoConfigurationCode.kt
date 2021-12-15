package com.daangn

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Repository
class MyRepository

@Service
class MyService(myRepository: MyRepository) {
    fun say() = "Hello World!"
}

@RestController
@SpringBootApplication
class MyApplication {

    @Autowired
    private lateinit var myService: MyService

    @RequestMapping("/")
    fun home(): String {
        return myService.say()
    }
}

fun main(args: Array<String>) {
    runApplication<MyApplication>(*args)
}
