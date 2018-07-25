# ktor-spring sample
ktor spring integration sample.still experimental.

ktor http://ktor.io/

ktor Guice sample [http://ktor.io/samples/guice.html]

Spring Framework [https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/index.html]


## Spring Integration

setupSpringApplicationContextInfrastructure　function

```kotlin
fun main(args: Array<String>) {

    val applicationContext = createApplicationContext(ApplicationConfig::class.java)

    val server = embeddedServer(Netty,
            port = 3333) {
                install(CallLogging)

                setupSpringApplicationContextInfrastructure(applicationContext)

                routing {
                    sampleRoute()

                }
            }

    server.start(wait = true)
}
```

### Request Handler

bean function

```kotlin
fun Routing.sampleRoute() {

    get("/") {

        val service = call.bean(SampleService::class.java) //getBean
        val result = service.exec()

        call.respondText(result.toString(), ContentType.Text.Plain)
    }
}

```

transaction management

```kotlin
fun Routing.sampleRoute() {

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
    
    get("/insert") {

        val paramId = call.parameters["id"]
        val id = if (null != paramId) paramId else ""

        val paramName = call.parameters["name"]
        val name = if (null != paramName) paramName else ""

        val todo = Todo(id.toInt(), name)

        call.inTransaction(SampleService::class.java) { service ->
            service.insert(todo)
        }

        call.respondText(todo.toString(), ContentType.Text.Plain)
    }
    
}

```