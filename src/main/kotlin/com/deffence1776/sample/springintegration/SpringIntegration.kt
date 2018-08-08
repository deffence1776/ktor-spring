package com.deffence1776.sample.springintegration

import io.ktor.application.*
import io.ktor.pipeline.PipelinePhase
import io.ktor.util.AttributeKey
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext


//key for ApplicationContext
private val cxtKey = AttributeKey<ApplicationContext>("ctx")

class SpringIntegration{

    class Configuration {
        internal var ctx:AnnotationConfigApplicationContext =  AnnotationConfigApplicationContext()
    }

    companion object Feature : ApplicationFeature<Application, Configuration, SpringIntegration> {
        override val key: AttributeKey<SpringIntegration> = AttributeKey("spring Integration")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): SpringIntegration {
            val setupApplicationContextPhase = PipelinePhase("setup ApplicationContext")
            val configuration = Configuration().apply(configure)
            val feature = SpringIntegration()
            pipeline.insertPhaseBefore(ApplicationCallPipeline.Infrastructure, setupApplicationContextPhase)
            pipeline.intercept(setupApplicationContextPhase) {
                call.attributes.put(cxtKey, configuration.ctx)
                proceed()

            }
            return feature
        }
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



