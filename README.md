# ktor-spring sample
ktor spring integration sample.still experimental.

[ktor](http://ktor.io/)

[ktor Guice sample](http://ktor.io/samples/guice.html)

[Spring Framework](https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/index.html)

[MyBatis3](http://www.mybatis.org/mybatis-3/)

[MyBatis-Spring](http://www.mybatis.org/spring/)

## Spring Integration

create Server with Spring ApplicationContext.

```kotlin
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
```

### Request Handler

use applicationContext 

```kotlin
fun Routing.sampleRoute() {

    get("/") {

        val service = call.bean(SampleService::class.java) //getBean
        //val service =  call.applicationContext.getBean(SampleService::class.java)
        val result = service.exec()

        call.respondText(result.toString(), ContentType.Text.Plain)
    }
    
}

```

transaction management.

```kotlin
fun Routing.sampleRoute() {

    get("/view") {
        val service = call.bean(SampleService::class.java) //getBean
        val result = call.inTransaction({ 
             service.exec()
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