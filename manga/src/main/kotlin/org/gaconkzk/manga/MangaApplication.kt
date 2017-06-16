package org.gaconkzk.manga

import org.gaconkzk.manga.util.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(MangaProperties::class)
class MangaApplication

fun main(args: Array<String>) {
    run(MangaApplication::class, *args)
}
