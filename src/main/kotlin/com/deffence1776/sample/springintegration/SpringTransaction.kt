package com.deffence1776.sample.springintegration

import io.ktor.application.ApplicationCall
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate


/**
 * トランザクション管理
 */
fun <OUT> ApplicationCall.inTransaction(serviceFun: () -> OUT): OUT {
    return bean(TransactionTemplate::class.java).execute(TransactionCallback {
        return@TransactionCallback serviceFun()
    })
}

/**
 * トランザクション管理
 */
fun <S, OUT> ApplicationCall.inTransaction(serviceType: Class<S>, serviceFun: (S) -> OUT): OUT {
    val service = bean(serviceType)
    return bean(TransactionTemplate::class.java).execute(TransactionCallback {
        return@TransactionCallback serviceFun(service)
    })
}

/**
 * トランザクション管理
 */
fun <S, OUT> ApplicationCall.inTransaction(serviceName: String, serviceType: Class<S>, serviceFun: (S) -> OUT): OUT {
    val service = bean(serviceName) as S
    return bean(TransactionTemplate::class.java).execute(TransactionCallback {
        return@TransactionCallback serviceFun(service)
    })
}