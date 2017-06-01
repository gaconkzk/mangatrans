package org.gaconkzk.manga.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.toMono

/**
 *
 */
@Component
class MangaHandler {
    fun hello(req: ServerRequest) = ok().body("hello".toMono())
}