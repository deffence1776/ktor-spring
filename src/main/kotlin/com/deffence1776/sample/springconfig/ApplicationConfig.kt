package com.deffence1776.sample.springconfig

import org.springframework.context.annotation.Configuration
import javax.sql.DataSource
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.HikariConfig
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate


@Configuration
@ComponentScan("com.deffence1776.sample.service")
@MapperScan("com.deffence1776.sample.mapper") //mapper
open class ApplicationConfig {

    @Bean
    open fun datasource(): DataSource =HikariDataSource(HikariConfig("/prop/db.properties"))

    @Bean
    open fun transactionManager(dataSource: DataSource): PlatformTransactionManager = DataSourceTransactionManager(dataSource)

    @Bean
    open fun transactionTemplate(transactionManager: PlatformTransactionManager): TransactionTemplate = TransactionTemplate(transactionManager)

    @Bean
    open fun jdbcTemplate(dataSource: DataSource): JdbcTemplate = JdbcTemplate(dataSource)

    /**
     * Mybatis SessionFactory
     */
    @Bean
    open fun sqlSessionFactoryBean(dataSource: DataSource): SqlSessionFactoryBean {
        val bean = SqlSessionFactoryBean()
        bean.setDataSource(dataSource)
        return bean
    }

    @Bean
    open fun init(jdbcTemplate: JdbcTemplate,transactionTemplate: TransactionTemplate): String {
        transactionTemplate.execute{
            jdbcTemplate.update("""
                drop table if exists  todo
            """.trimIndent())


            jdbcTemplate.update("""
                create table todo(
                                id integer primary key,
                                name varchar
                            )
                        """.trimIndent())

            jdbcTemplate.update("""
                insert into todo values(1,'task1');

                insert into todo values(2,'task2');
                        """.trimIndent())
        }
        return "OK"
    }
}