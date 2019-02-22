package io.realworld.shared.infrastructure

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Factory
class DatabaseConfiguration(val dataSource: DataSource) {

    @Bean
    @Replaces(DataSourceTransactionManager::class)
    fun transactionManager() = SpringTransactionManager(dataSource)

    @Bean // PersistenceExceptionTranslationPostProcessor with proxyTargetClass=false, see https://github.com/spring-projects/spring-boot/issues/1844
    fun persistenceExceptionTranslationPostProcessor() = PersistenceExceptionTranslationPostProcessor()
}
