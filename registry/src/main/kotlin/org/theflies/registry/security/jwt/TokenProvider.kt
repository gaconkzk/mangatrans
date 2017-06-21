package org.theflies.registry.security.jwt

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.theflies.registry.config.TheFliesProperties
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import kotlin.streams.toList

@Component
class TokenProvider(private val fliesProperties: TheFliesProperties) {

  private val log = LoggerFactory.getLogger(TokenProvider::class.java)

  private var secretKey: String? = null

  private var tokenValidityInMilliseconds: Long = 0

  private var tokenValidityInMillisecondsForRememberMe: Long = 0

  @PostConstruct
  fun init() {
    this.secretKey = fliesProperties.security.authentication.jwt.secret

    this.tokenValidityInMilliseconds =
        1000 *
            fliesProperties.security.authentication.jwt.tokenValidityInSeconds
    this.tokenValidityInMillisecondsForRememberMe =
        1000 *
            fliesProperties.security.authentication.jwt.tokenValidityInSecondsForRememberMe
  }

  fun createToken(authentication: Authentication, rememberMe: Boolean?): String {
    val authorities = authentication.authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","))

    val now = Date().time
    val validity: Date
    if (rememberMe!!) {
      validity = Date(now + this.tokenValidityInMillisecondsForRememberMe)
    } else {
      validity = Date(now + this.tokenValidityInMilliseconds)
    }

    return Jwts.builder()
        .setSubject(authentication.name)
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .setExpiration(validity)
        .compact()
  }

  fun getAuthentication(token: String): Authentication {
    val claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .body

    val authorities = (claims[AUTHORITIES_KEY].toString().split(","))
        .stream()
        .map { SimpleGrantedAuthority(it) }
        .toList()

    val principal = User(claims.subject, "", authorities)

    return UsernamePasswordAuthenticationToken(principal, "", authorities)
  }

  fun validateToken(authToken: String): Boolean {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken)
      return true
    } catch (e: SignatureException) {
      log.info("Invalid JWT signature.")
      log.trace("Invalid JWT signature trace: {}", e)
    } catch (e: MalformedJwtException) {
      log.info("Invalid JWT token.")
      log.trace("Invalid JWT token trace: {}", e)
    } catch (e: ExpiredJwtException) {
      log.info("Expired JWT token.")
      log.trace("Expired JWT token trace: {}", e)
    } catch (e: UnsupportedJwtException) {
      log.info("Unsupported JWT token.")
      log.trace("Unsupported JWT token trace: {}", e)
    } catch (e: IllegalArgumentException) {
      log.info("JWT token compact of handler are invalid.")
      log.trace("JWT token compact of handler are invalid trace: {}", e)
    }

    return false
  }

  companion object {
    private val AUTHORITIES_KEY = "auth"
  }
}
