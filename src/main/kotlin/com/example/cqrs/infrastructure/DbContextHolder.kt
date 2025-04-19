package com.example.cqrs.infrastructure

object DbContextHolder {
    private val context = ThreadLocal<String>()

    fun setRoutingKey(key: String) = context.set(key)
    fun getRoutingKey(): String? = context.get()
    fun clearRoutingKey() = context.remove()
}