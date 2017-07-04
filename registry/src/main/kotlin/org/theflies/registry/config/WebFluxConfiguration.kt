package org.theflies.registry.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.WebFilter

@Configuration
@EnableWebFlux
class WebFluxConfiguration {

  @Bean
  fun httpHandler(routes: RouterFunction<ServerResponse>, springSecurityFilterChain: WebFilter): HttpHandler {
    val handlerStrategies = HandlerStrategies.builder()
        .webFilter(springSecurityFilterChain)
        .build()

    return RouterFunctions.toHttpHandler(routes, handlerStrategies)
  }
}