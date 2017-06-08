package org.gaconkzk.manga.handler

import org.gaconkzk.manga.repository.MangaRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body

/**
 *
 */
@Component
class MangaHandler(val mangarepo: MangaRepository) {
    fun all(req: ServerRequest) = ok().body(mangarepo.findAll())
}