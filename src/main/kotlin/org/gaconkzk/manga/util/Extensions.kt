package org.gaconkzk.manga.util

import org.springframework.boot.SpringApplication
import kotlin.reflect.KClass

/**
 *
 */
// ------------
// Spring Boot extensions
// ------------
fun run(type: KClass<*>, vararg args: String) = SpringApplication.run(type.java, *args)