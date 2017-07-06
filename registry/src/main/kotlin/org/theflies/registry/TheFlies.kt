package org.theflies.registry

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment
import org.theflies.registry.config.TheFliesConstants
import org.theflies.registry.config.TheFliesProperties
import org.theflies.registry.util.addDefaultProfile
import java.net.InetAddress
import javax.annotation.PostConstruct


/**
 *
 */
@SpringBootApplication
@EnableEurekaServer
@EnableConfigServer
@ComponentScan
@EnableAutoConfiguration(exclude = arrayOf(
    MetricFilterAutoConfiguration::class,
    MetricRepositoryAutoConfiguration::class,
    MetricsDropwizardAutoConfiguration::class
))
@EnableConfigurationProperties(TheFliesProperties::class)
@EnableDiscoveryClient
@EnableZuulProxy
class TheFlies(val env: Environment) {
  val log = LoggerFactory.getLogger(TheFlies::class.java)
//
//  @Value("\${server.port:8080}")
//  private val port = 8080
//
//  @Bean
//  fun nettyContext(handler: HttpHandler): NettyContext {
//    val adapter = ReactorHttpHandlerAdapter(handler)
//    val httpServer = HttpServer.create("localhost", port)
//    return httpServer.newHandler(adapter).block()
//  }

  @PostConstruct
  fun initApplication() {
    val activeProfiles = env.activeProfiles
    when {
      activeProfiles.contains(TheFliesConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles
          .contains(TheFliesConstants.SPRING_PROFILE_PRODUCTION) ->
        log.error("You have misconfigured your application! It should not run "
            + "with both the 'dev' and 'prod' profiles at the same time.")
      activeProfiles.contains(TheFliesConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles
          .contains(TheFliesConstants.SPRING_PROFILE_CLOUD) ->
        log.error("You have misconfigured your application! It should not"
            + "run with both the 'dev' and 'cloud' profiles at the same time.")
    }
  }
}

fun main(args: Array<String>) {
  val log = LoggerFactory.getLogger(TheFlies::class.java)

  val app = SpringApplication(TheFlies::class.java)
  // Running this web application in Reactive mode
  app.webApplicationType = WebApplicationType.REACTIVE
  app.addDefaultProfile()

  val env = app.run(*args).environment
  val protocol =
      if (env.getProperty("server.ssl.key-store") != null) {
        "https"
      } else {
        "http"
      }

  log.info(
      "\n----------------------------------------------------------\n\t" +
          "Application '${env.getProperty("spring.application.name")}' is running! Access URLs:\n\t" +
          "Local: \t\t$protocol://localhost:${env.getProperty("server.port")}\n\t" +
          "External: \t$protocol://${InetAddress.getLocalHost().hostAddress}:${env.getProperty("server.port")}\n\t" +
          "Profile(s): \t${env.activeProfiles.joinToString()}" +
          "\n----------------------------------------------------------")

  when (env.getProperty("theflies.security.authentication.jwt.secret")) {
    null ->
      log.error("\n----------------------------------------------------------\n" +
          "Your JWT secret key is not set up, you will not be able to log into the JHipster.\n" +
          "Please read the documentation at https://theflies.github.io/theflies-registry/\n" +
          "----------------------------------------------------------")
    "this-secret-should-not-be-used-read-the-comment" ->
      log.error("\n----------------------------------------------------------\n" +
          "Your JWT secret key is not configured using Spring Cloud Config, you will not be able to \n"
          +
          "use the JHipster Registry dashboards to monitor external applications. \n" +
          "Please read the documentation at https://theflies.github.io/theflies-registry/\n" +
          "----------------------------------------------------------")
  }
}
