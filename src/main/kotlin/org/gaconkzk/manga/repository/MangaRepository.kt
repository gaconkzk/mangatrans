package org.gaconkzk.manga.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.gaconkzk.manga.domain.Manga
import org.gaconkzk.manga.util.count
import org.gaconkzk.manga.util.drop
import org.gaconkzk.manga.util.findAll
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

/**
 *
 */
@Repository
class MangaRepository(val template: ReactiveMongoTemplate, val objectMapper: ObjectMapper) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun initData() {
        if (count().block() == 0L) {
            val mangasResource = ClassPathResource("data/mangas.json")
            val mangas: List<Manga> = objectMapper.readValue(mangasResource.inputStream)
            mangas.forEach { save(it).block() }
            logger.info("Mangas data initialization completed")
        }
    }

    fun drop() = template.drop<Manga>()

    fun count() = template.count<Manga>()

    fun save(manga: Manga) = template.save(manga)

    fun save(manga: Mono<Manga>) = template.save(manga)

    fun findAll() = template.findAll<Manga>()
}
