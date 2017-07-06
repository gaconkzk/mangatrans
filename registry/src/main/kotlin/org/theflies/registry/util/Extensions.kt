package org.theflies.registry.util

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.web.reactive.function.server.ServerResponse
import org.theflies.registry.TheFlies
import kotlin.reflect.KClass

private val log = LoggerFactory.getLogger(TheFlies::class.java)
private val SPRING_PROFILE_DEFAULT = "spring.profiles.default"

/**
 *
 */
// ------------
// Spring Boot extensions
// ------------
fun SpringApplication.addDefaultProfile(vararg profiles: String) {
  if (profiles.joinToString().isNotEmpty()) {
    setDefaultProperties(kotlin.collections.mapOf(SPRING_PROFILE_DEFAULT to profiles
        .joinToString()))
  }
}

fun run(type: KClass<*>, vararg args: String): ApplicationContext =
    SpringApplication.run(type.java, *args)

fun ServerResponse.BodyBuilder.json() = contentType(APPLICATION_JSON_UTF8)
