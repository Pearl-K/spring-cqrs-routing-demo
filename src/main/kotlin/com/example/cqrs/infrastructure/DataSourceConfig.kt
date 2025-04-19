package com.example.cqrs.infrastructure

import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.write")
    fun writeDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    @ConfigurationProperties("spring.datasource.read")
    fun readDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    @Primary
    fun routingDataSource(): DataSource {
        val routingDataSource = ReplicationRoutingDataSource()
        val dataSources: MutableMap<Any, Any> = mutableMapOf()
        dataSources["write"] = writeDataSource()
        dataSources["read"] = readDataSource()

        routingDataSource.setTargetDataSources(dataSources)
        routingDataSource.setDefaultTargetDataSource(writeDataSource())
        return routingDataSource
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("com.example.cqrs.domain")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }

    @Bean
    fun transactionManager(
        emf: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(emf)
    }
}