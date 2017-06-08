package org.gaconkzk.manga

import org.gaconkzk.manga.handler.MangaHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

/**
 *
 */
@Configuration
class ApiRoutes(val mangaHandler: MangaHandler) {
    @Bean
    fun apiRouter() = router {
        (accept(APPLICATION_JSON) and "/api").nest {
            "/mangas".nest {
                GET("/", mangaHandler::all)
            }

            "/img".nest {
                GET("/", mangaHandler::img)
            }
        }
    }
}