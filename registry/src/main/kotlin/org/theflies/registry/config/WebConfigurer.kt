package org.theflies.registry.config

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.servlet.InstrumentedFilter
import com.codahale.metrics.servlets.MetricsServlet
import io.undertow.UndertowOptions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
import org.springframework.boot.web.server.MimeMappings
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.theflies.registry.web.filter.CachingHttpHeadersFilter
import java.io.File
import java.nio.file.Paths
import java.util.*
import javax.servlet.DispatcherType
import javax.servlet.ServletContext
import javax.servlet.ServletException

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
class WebConfigurer(private val env: Environment,
                    private val fliesProperties: TheFliesProperties) :
    ServletContextInitializer,
    WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

  private val log = LoggerFactory.getLogger(WebConfigurer::class.java)

  private var metricRegistry: MetricRegistry? = null

  @Throws(ServletException::class)
  override fun onStartup(servletContext: ServletContext) {
    if (env.activeProfiles.isNotEmpty()) {
      log.info("Web application configuration, using profiles: {}", *env.activeProfiles as Array<*>)
    }
    val disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC)
    initMetrics(servletContext, disps)
    if (env.acceptsProfiles(TheFliesConstants.SPRING_PROFILE_PRODUCTION)) {
      initCachingHttpHeadersFilter(servletContext, disps)
    }
    log.info("Web application fully configured")
  }

  /**
   * Customize the Servlet engine: Mime types, the document root, the cache.
   */
  override fun customize(container: UndertowServletWebServerFactory) {
    val mappings = MimeMappings(MimeMappings.DEFAULT)
    // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
    mappings.add("html", "text/html;charset=utf-8")
    // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
    mappings.add("json", "text/html;charset=utf-8")
    container.mimeMappings = mappings
    // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
    setLocationForStaticAssets(container)

    /*
     * Enable HTTP/2 for Undertow - https://twitter.com/ankinson/status/829256167700492288
     * HTTP/2 requires HTTPS, so HTTP requests will fallback to HTTP/1.1.
     * See the JHipsterProperties class and your application-*.yml configuration files
     * for more information.
     */
    if (fliesProperties.http.version == TheFliesProperties.Http.Version.V_2_0 && container is UndertowServletWebServerFactory) {

      container
          .addBuilderCustomizers(
              UndertowBuilderCustomizer {
                it.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
              })
    }
  }

  private fun setLocationForStaticAssets(container: ConfigurableServletWebServerFactory) {
    val root: File
    val prefixPath = resolvePathPrefix()
    root = File(prefixPath + "target/www/")
    if (root.exists() && root.isDirectory) {
      container.setDocumentRoot(root)
    }
  }

  /**
   * Resolve path prefix to static resources.
   */
  private fun resolvePathPrefix(): String {
    val fullExecutablePath = this.javaClass.getResource("").path
    val rootPath = Paths.get(".").toUri().normalize().path
    val extractedPath = fullExecutablePath.replace(rootPath, "")
    val extractionEndIndex = extractedPath.indexOf("target/")
    if (extractionEndIndex <= 0) {
      return ""
    }
    return extractedPath.substring(0, extractionEndIndex)
  }

  /**
   * Initializes the caching HTTP Headers Filter.
   */
  private fun initCachingHttpHeadersFilter(servletContext: ServletContext,
                                           disps: EnumSet<DispatcherType>) {
    log.debug("Registering Caching HTTP Headers Filter")
    val cachingHttpHeadersFilter = servletContext.addFilter("cachingHttpHeadersFilter",
        CachingHttpHeadersFilter(fliesProperties))

    cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*")
    cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*")
    cachingHttpHeadersFilter.setAsyncSupported(true)
  }

  /**
   * Initializes Metrics.
   */
  private fun initMetrics(servletContext: ServletContext, disps: EnumSet<DispatcherType>) {
    log.debug("Initializing Metrics registries")
    servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE,
        metricRegistry)
    servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY,
        metricRegistry)

    log.debug("Registering Metrics Filter")
    val metricsFilter = servletContext.addFilter("webappMetricsFilter",
        InstrumentedFilter())

    metricsFilter.addMappingForUrlPatterns(disps, true, "/*")
    metricsFilter.setAsyncSupported(true)

    log.debug("Registering Metrics Servlet")
    val metricsAdminServlet = servletContext.addServlet("metricsServlet", MetricsServlet())

    metricsAdminServlet.addMapping("/management/metrics/*")
    metricsAdminServlet.setAsyncSupported(true)
    metricsAdminServlet.setLoadOnStartup(2)
  }

  @Bean
  fun corsFilter(): CorsFilter {
    val source = UrlBasedCorsConfigurationSource()
    val config = fliesProperties.cors
    if (config.allowedOrigins != null && !config.allowedOrigins.isEmpty()) {
      log.debug("Registering CORS filter")
      source.registerCorsConfiguration("/api/**", config)
      source.registerCorsConfiguration("/v2/api-docs", config)
      source.registerCorsConfiguration("/*/api/**", config)
    }
    return CorsFilter(source)
  }

  @Autowired(required = false)
  fun setMetricRegistry(metricRegistry: MetricRegistry) {
    this.metricRegistry = metricRegistry
  }
}
