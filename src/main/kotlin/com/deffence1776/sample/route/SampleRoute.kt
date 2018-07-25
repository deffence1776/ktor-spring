package com.deffence1776.sample.route


import com.deffence1776.sample.model.Todo
import com.deffence1776.sample.service.SampleService
import com.deffence1776.sample.springintegration.bean
import com.deffence1776.sample.springintegration.inTransaction
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.param
import io.ktor.routing.post

fun Routing.sampleRoute() {

    //
    get("/") {

        val service = call.bean(SampleService::class.java)
        val result = service.exec()

        call.respondText(result.toString(), ContentType.Text.Plain)
    }


    get("/type") {
        val result = call.inTransaction(SampleService::class.java) { service ->
            service.exec()
        }
        call.respondText(result.toString(), ContentType.Text.Plain)
    }

    get("/name") {
        val result = call.inTransaction("sampleService", SampleService::class.java) { service ->
            service.exec2()
        }
        call.respondText(result.toString(), ContentType.Text.Plain)
    }

    //insert By query String
    get("/insert"){

        val paramId= call.parameters["id"]
        val id=if(null != paramId) paramId else ""

        val paramName= call.parameters["name"]
        val name=if(null != paramName) paramName else ""

        val todo= Todo(id.toInt(),name)

        call.inTransaction (SampleService::class.java){
            it.insert(todo)
        }

        call.respondText(todo.toString(), ContentType.Text.Plain)
    }


}