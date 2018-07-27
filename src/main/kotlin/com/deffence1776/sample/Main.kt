package com.anderprime.scoremaster.app

import com.deffence1776.sample.route.sampleRoute
import com.deffence1776.sample.springconfig.ApplicationConfig
import com.deffence1776.sample.springintegration.SpringIntegration
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {

    val server = embeddedServer(Netty,
            port = 3333) {
                install(CallLogging)

                install(SpringIntegration){
                    ctx.register(ApplicationConfig::class.java)
                    ctx.refresh()
                }
                routing {
                    sampleRoute()

                }
            }

    server.start(wait = true)
}



