package org.theflies.registry.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.cors.CorsConfiguration
import java.util.*
import javax.validation.constraints.NotNull

/**
 * Properties specific to The Flies.

 *
 *  Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "theflies", ignoreUnknownFields = false)
class TheFliesProperties {

  val async = Async()

  val http = Http()

  val cache = Cache()

  val mail = Mail()

  val security = Security()

  val swagger = Swagger()

  val metrics = Metrics()

  val cors = CorsConfiguration()

  val social = Social()

  val gateway = Gateway()

  val ribbon = Ribbon()

  private val registry = Registry()

  class Async {

    var corePoolSize = 2

    var maxPoolSize = 50

    var queueCapacity = 10000
  }

  class Http {

    enum class Version {
      V_1_1, V_2_0
    }

    val cache = Cache()

    /**
     * HTTP version, must be "V_1_1" (for HTTP/1.1) or V_2_0 (for (HTTP/2)
     */
    var version = Version.V_1_1

    class Cache {
      var timeToLiveInDays = 1461L
    }
  }

  class Cache {

    val hazelcast = Hazelcast()

    val ehcache = Ehcache()

    class Hazelcast {

      var timeToLiveSeconds = 3600

      var backupCount = 1
    }

    class Ehcache {

      var timeToLiveSeconds = 3600

      var maxEntries: Long = 100
    }
  }

  class Mail {

    var from = ""

    var baseUrl = ""
  }

  class Security {

    val rememberMe = RememberMe()

    val clientAuthorization = ClientAuthorization()

    val authentication = Authentication()

    class ClientAuthorization {

      var accessTokenUri: String? = null

      var tokenServiceId: String? = null

      var clientId: String? = null

      var clientSecret: String? = null
    }

    class Authentication {

      val oauth = Oauth()

      val jwt = Jwt()

      class Oauth {

        var clientId: String? = null

        var clientSecret: String? = null

        var tokenValidityInSeconds = 1800
      }

      class Jwt {

        var secret: String? = null

        var tokenValidityInSeconds: Long = 1800

        var tokenValidityInSecondsForRememberMe: Long = 2592000
      }
    }

    class RememberMe {

      @NotNull
      var key: String? = null
    }
  }

  class Swagger {

    var title = "Application API"

    var description = "API documentation"

    var version = "0.0.1"

    var termsOfServiceUrl: String? = null

    var contactName: String? = null

    var contactUrl: String? = null

    var contactEmail: String? = null

    var license: String? = null

    var licenseUrl: String? = null

    var defaultIncludePattern = "/api/.*"
  }

  class Metrics {

    val jmx = Jmx()

    val graphite = Graphite()

    val prometheus = Prometheus()

    val logs = Logs()

    class Jmx {

      var isEnabled = true
    }

    class Graphite {

      var isEnabled = false

      var host = "localhost"

      var port = 2003

      var prefix = "jhipsterApplication"
    }

    class Prometheus {

      var isEnabled = false

      var endpoint = "/prometheusMetrics"
    }

    class Logs {

      var isEnabled = false

      var reportFrequency: Long = 60
        private set

      fun setReportFrequency(reportFrequency: Int) {
        this.reportFrequency = reportFrequency.toLong()
      }
    }
  }

  val logging = Logging()

  class Logging {

    val logstash = Logstash()

    class Logstash {

      var isEnabled = false

      var host = "localhost"

      var port = 5000

      var queueSize = 512
    }

    val spectatorMetrics = SpectatorMetrics()

    class SpectatorMetrics {

      var isEnabled = false
    }
  }

  class Social {

    var redirectAfterSignIn = "/#/home"
  }

  class Gateway {

    val rateLimiting = RateLimiting()

    var authorizedMicroservicesEndpoints: Map<String, List<String>> = LinkedHashMap()

    class RateLimiting {

      var isEnabled = false

      var limit = 100_000L

      var durationInSeconds = 3600
    }
  }

  class Ribbon {

    var displayOnActiveProfiles: Array<String>? = null
  }

  private class Registry {

    var password: String? = null
  }
}
