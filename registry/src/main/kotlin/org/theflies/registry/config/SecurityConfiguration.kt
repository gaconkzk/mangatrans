package org.theflies.registry.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.HttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


/**
 *
 */
@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration {

  @Bean
  @Throws(Exception::class)
  fun httpSecurity(http: HttpSecurity): SecurityWebFilterChain {
    return http
        .authorizeExchange()
        .pathMatchers("/**").permitAll()
        .pathMatchers("/api/authorize").permitAll()
        .anyExchange().authenticated()
        .and()
        .build()
  }

  @Bean
  fun reactiveAuthManager(auth: AuthenticationManager) = ReactiveAuthenticationManagerAdapter(auth)

}
