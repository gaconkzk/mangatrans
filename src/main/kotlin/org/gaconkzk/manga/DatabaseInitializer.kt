package org.gaconkzk.manga

import org.gaconkzk.manga.repository.MangaRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 *
 */
@Component
class DatabaseInitializer(val mangaRepository: MangaRepository) {

    @PostConstruct
    fun init() {
        mangaRepository.initData()
    }
}