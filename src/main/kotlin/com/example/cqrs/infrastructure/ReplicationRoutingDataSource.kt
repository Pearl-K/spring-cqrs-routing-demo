package com.example.cqrs.infrastructure

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class ReplicationRoutingDataSource : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): Any {
        return DbContextHolder.getRoutingKey() ?: "write"
    }
}