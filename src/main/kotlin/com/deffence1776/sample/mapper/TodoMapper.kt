package com.deffence1776.sample.mapper

import com.deffence1776.sample.model.Todo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface TodoMapper{
    @Select("""
        select
         *
        from todo
    """)
    fun findAll(): List<Todo>

    @Select("""
        select
         *
        from todo
        where id =#{id}
    """)
    fun findById(id:Int): Todo


    @Insert("""
        insert into todo values(#{id},#{name});
    """)
    fun insert(todo:Todo)
}
