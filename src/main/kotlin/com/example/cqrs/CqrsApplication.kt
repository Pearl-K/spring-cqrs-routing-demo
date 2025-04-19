package com.example.cqrs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class CqrsApplication

fun main(args: Array<String>) {
    runApplication<CqrsApplication>(*args)
}
