package org.theflies.registry.util

import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import org.springframework.web.reactive.function.server.ServerResponse
import kotlin.reflect.KClass
import org.springframework.http.MediaType.*

/**
 *
 */
// ------------
// Spring Boot extensions
// ------------
fun run(type: KClass<*>, vararg args: String): ApplicationContext =
    SpringApplication.run(type.java, *args)

fun ServerResponse.BodyBuilder.json() = contentType(APPLICATION_JSON_UTF8)