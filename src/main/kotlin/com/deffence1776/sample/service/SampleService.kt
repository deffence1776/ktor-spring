package com.deffence1776.sample.service

import com.deffence1776.sample.mapper.TodoMapper
import com.deffence1776.sample.model.Todo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

/**
 * inject JdbcTemplate and TodoMapper
 */
@Service
open class SampleService(val JdbcTemplate:JdbcTemplate,val mapper:TodoMapper) {

    /**
     * use JdbcTemplate
     */
    fun exec(): MutableMap<String, Any>? {
        return JdbcTemplate.queryForMap("""
                    select
                     *
                    from
                     todo
                    where id = 2
                """.trimIndent())
    }

    /**
     * use MybatisMapper
     */
    fun exec2():List<Todo> = mapper.findAll()

    fun insert(todo: Todo){
        mapper.insert(todo)
    }
}

