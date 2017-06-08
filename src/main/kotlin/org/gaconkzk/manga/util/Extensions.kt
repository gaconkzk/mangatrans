package org.gaconkzk.manga.util

import org.springframework.boot.SpringApplication
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

/**
 *
 */
// ------------
// Spring Boot extensions
// ------------
fun run(type: KClass<*>, vararg args: String) = SpringApplication.run(type.java, *args)

// ----------------------
// Spring Data extensions
// ----------------------
inline fun <reified T : Any> ReactiveMongoOperations.count(): Mono<Long> =
        count(Query(), T::class.java)

inline fun <reified T : Any> ReactiveMongoOperations.drop(): Mono<Void> =
        dropCollection(T::class.java)

inline fun <reified T : Any> ReactiveMongoOperations.findAll(): Flux<T> =
        findAll(T::class.java)