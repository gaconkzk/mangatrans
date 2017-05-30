package org.gaconkzk.manga

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MangaApplication

fun main(args: Array<String>) {
    SpringApplication.run(MangaApplication::class.java, *args)
}
