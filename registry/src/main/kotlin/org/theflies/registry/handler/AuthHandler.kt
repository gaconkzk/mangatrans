package org.theflies.registry.handler

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors.toMono
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.theflies.registry.security.jwt.TokenProvider
import org.theflies.registry.util.json
import org.theflies.registry.web.rest.vm.AuthExceptionVM
import org.theflies.registry.web.rest.vm.AuthResponseVM
import org.theflies.registry.web.rest.vm.LoginVM
import reactor.core.publisher.Mono

/**
 *
 */
@Component
class AuthHandler(val tokenProvider: TokenProvider, val auth: ReactiveAuthenticationManager) {
  fun authorize(req: ServerRequest): Mono<ServerResponse> {

    var rememberMe:Boolean = false

    return req.body(toMono(LoginVM::class.java)).map {
      rememberMe = it.isRememberMe?:false
      UsernamePasswordAuthenticationToken(it.username, it.password)
    }.map {
      auth.authenticate(it).block() // :( old style
    }.map {
      tokenProvider.createToken(it, rememberMe)
    }.map {
      AuthResponseVM(it, null)
    }.flatMap {
      ok().json().body(fromObject(it))
    }.switchIfEmpty(
        status(HttpStatus.UNAUTHORIZED)
            .json()
            .body(fromObject(
                AuthExceptionVM(401, "Invalid username and password combination"))
            )
    )
  }
}