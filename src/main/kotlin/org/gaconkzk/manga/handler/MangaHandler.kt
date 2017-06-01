package org.gaconkzk.manga.handler

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 *
 */
@Component
class MangaHandler {
    fun hello(): Mono<String> = Mono.just("hello")
}