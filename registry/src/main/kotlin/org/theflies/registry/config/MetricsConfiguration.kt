package org.theflies.registry.config

import com.codahale.metrics.JmxReporter
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Slf4jReporter
import com.codahale.metrics.health.HealthCheckRegistry
import com.codahale.metrics.jvm.*
import com.netflix.spectator.api.Registry
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.autoconfigure.ExportMetricReader
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter
import org.springframework.boot.actuate.metrics.writer.MetricWriter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.netflix.metrics.spectator.SpectatorMetricReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.theflies.registry.config.metrics.SpectatorLogMetricWriter
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

/**
 *
 */

@Configuration
@EnableMetrics(proxyTargetClass = true)
class MetricsConfiguration(private val fliesProperties: TheFliesProperties) : MetricsConfigurerAdapter() {
  private val log = LoggerFactory.getLogger(MetricsConfiguration::class.java)

  private val metricRegistry = MetricRegistry()

  private val healthCheckRegistry = HealthCheckRegistry()

  @Bean
  override fun getMetricRegistry(): MetricRegistry {
    return metricRegistry
  }

  @Bean
  override fun getHealthCheckRegistry(): HealthCheckRegistry {
    return healthCheckRegistry
  }

  @PostConstruct
  fun init() {
    log.debug("Registering JVM gauges")
    metricRegistry.register(PROP_METRIC_REG_JVM_MEMORY, MemoryUsageGaugeSet())
    metricRegistry.register(PROP_METRIC_REG_JVM_GARBAGE, GarbageCollectorMetricSet())
    metricRegistry.register(PROP_METRIC_REG_JVM_THREADS, ThreadStatesGaugeSet())
    metricRegistry.register(PROP_METRIC_REG_JVM_FILES, FileDescriptorRatioGauge())
    metricRegistry.register(PROP_METRIC_REG_JVM_BUFFERS, BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()))
    if (fliesProperties.metrics.jmx.isEnabled) {
      log.debug("Initializing Metrics JMX reporting")
      val jmxReporter = JmxReporter.forRegistry(metricRegistry).build()
      jmxReporter.start()
    }
    if (fliesProperties.metrics.logs.isEnabled) {
      log.info("Initializing Metrics Log reporting")
      val reporter = Slf4jReporter.forRegistry(metricRegistry)
          .outputTo(LoggerFactory.getLogger("metrics"))
          .convertRatesTo(TimeUnit.SECONDS)
          .convertDurationsTo(TimeUnit.MILLISECONDS)
          .build()
      reporter.start(fliesProperties.metrics.logs.reportFrequency, TimeUnit.SECONDS)
    }
  }

  /* Spectator metrics log reporting */
  @Bean
  @ConditionalOnProperty("theflies.logging.spectator-metrics.enabled")
  @ExportMetricReader
  fun SpectatorMetricReader(registry: Registry): SpectatorMetricReader {
    log.info("Initializing Spectator Metrics Log reporting")
    return SpectatorMetricReader(registry)
  }

  @Bean
  @ConditionalOnProperty("theflies.logging.spectator-metrics.enabled")
  @ExportMetricWriter
  internal fun metricWriter(): MetricWriter = SpectatorLogMetricWriter()

  companion object {

    private val PROP_METRIC_REG_JVM_MEMORY = "jvm.memory"
    private val PROP_METRIC_REG_JVM_GARBAGE = "jvm.garbage"
    private val PROP_METRIC_REG_JVM_THREADS = "jvm.threads"
    private val PROP_METRIC_REG_JVM_FILES = "jvm.files"
    private val PROP_METRIC_REG_JVM_BUFFERS = "jvm.buffers"
  }
}
