package com.example.cqrs.aspect

import com.example.cqrs.infrastructure.DbContextHolder
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
@Order(0)
class ReadOnlyRoutingAspect {

    @Before("@annotation(com.example.cqrs.annotation.ReadOnly)")
    fun markAsRead() {
        DbContextHolder.setRoutingKey("read")
    }

    @After("@annotation(com.example.cqrs.annotation.ReadOnly)")
    fun clear() {
        DbContextHolder.clearRoutingKey()
    }

}