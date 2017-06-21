package org.theflies.registry.util

import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass

/**
 *
 */
// ------------
// Spring Boot extensions
// ------------
fun run(type: KClass<*>, vararg args: String): ApplicationContext =
    SpringApplication.run(type.java, *args)
