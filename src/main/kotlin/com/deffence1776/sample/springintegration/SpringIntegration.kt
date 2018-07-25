package com.deffence1776.sample.springintegration

import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.util.AttributeKey
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext


fun createApplicationContext(vararg configs: Class<*>):ApplicationContext{
    //init Spring ApplicationContext
    val ctx = AnnotationConfigApplicationContext()
    configs.forEach { ctx.register(it) }
    ctx.refresh()
    return ctx
}

//key for ApplicationContext
private val cxtKey = AttributeKey<ApplicationContext>("applicationContext")

/**
 * set Spring ApplicationContext to Kotr's ApplicationCall Attributs
 */
fun Application.setupSpringApplicationContextInfrastructure(ctx:ApplicationContext) {
    intercept(ApplicationCallPipeline.Infrastructure) {
        call.attributes.put(cxtKey, ctx)
    }
}

//getter
val ApplicationCall.applicationContext: ApplicationContext get() = attributes[cxtKey]


/**
 * utility function for getting bean by type
 */
fun <T> ApplicationCall.bean(type: Class<T>) = applicationContext.getBean(type)

/**
 * utility function for getting bean by name
 */
fun ApplicationCall.bean(name: String) = applicationContext.getBean(name)


