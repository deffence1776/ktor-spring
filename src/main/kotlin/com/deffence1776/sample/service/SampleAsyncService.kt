package com.deffence1776.sample.service

import com.deffence1776.sample.mapper.TodoMapper
import org.springframework.scheduling.annotation.Async

open class SampleAsyncService(val todoMapper: TodoMapper) {

    @Async
    open fun exec(){
        todoMapper.insert
    }
}