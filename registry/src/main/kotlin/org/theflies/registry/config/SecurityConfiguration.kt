package org.theflies.registry.config

//import org.springframework.security.config.web.server.HttpSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.HttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.theflies.registry.security.AuthoritiesConstants
import org.theflies.registry.security.Http401UnauthorizedEntryPoint
import org.theflies.registry.security.jwt.JWTConfigurer
import org.theflies.registry.security.jwt.TokenProvider


/**
 *
 */
@Configuration
//@EnableWebSecurity
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(private val authenticationEntryPoint: Http401UnauthorizedEntryPoint,
                            private val tokenProvider: TokenProvider) : WebSecurityConfigurerAdapter() {

  @Bean
  @Throws(Exception::class)
  fun httpSecurity(http: HttpSecurity): SecurityWebFilterChain {
    return http
        .authorizeExchange()
        .pathMatchers("/api/authorize").permitAll()
        .anyExchange().authenticated()
        .and()
        .build()
  }

  @Bean
  fun reactiveAuthManager(auth: AuthenticationManager) = ReactiveAuthenticationManagerAdapter(auth)

  @Throws(Exception::class)
  override fun configure(web: WebSecurity?) {
    web!!.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/app/**/*.{js,html}")
        .antMatchers("/bower_components/**")
        .antMatchers("/content/**")
        .antMatchers("/test/**")
        .antMatchers("/h2-console/**")
  }

  @Throws(Exception::class)
  override fun configure(http: org.springframework.security.config.annotation.web.builders.HttpSecurity) {
    http
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic()
        .realmName("The Flies Registry")
        .and()
        .authorizeRequests()
        .antMatchers("/**").permitAll() // cannot reconnect without this line
        .antMatchers("/api/authenticate").permitAll()
        .antMatchers("/api/**").authenticated()
        .antMatchers("/eureka/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/config/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/management/health").permitAll()
        .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .anyRequest().authenticated() // always at the end
        .and()
        .apply(securityConfigurerAdapter())
  }

  private fun securityConfigurerAdapter() = JWTConfigurer(tokenProvider)

}
