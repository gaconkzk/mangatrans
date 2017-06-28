package org.theflies.registry.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router
import org.theflies.registry.handler.AuthHandler

/**
 *
 */
@Configuration
class ApiRoutes(val authHandler: AuthHandler) {
  @Bean
  fun apiRouter() = router {
    (accept(APPLICATION_JSON) and "/api").nest {
      "/authenticate".nest {
        POST("/", authHandler::authorize)
      }
    }
  }
}