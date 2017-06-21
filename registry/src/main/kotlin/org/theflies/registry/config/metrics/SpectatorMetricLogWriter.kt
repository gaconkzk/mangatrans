package org.theflies.registry.config.metrics

import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.metrics.Metric
import org.springframework.boot.actuate.metrics.writer.Delta
import org.springframework.boot.actuate.metrics.writer.MetricWriter

/**
 * Log reporter for Spring Boot Metrics.

 * Output Spring Boot metrics to logs, using the same format as Dropwizard's Sfl4jReporter.
 */
class SpectatorLogMetricWriter : MetricWriter {

  private val log = LoggerFactory.getLogger("metrics")

  override fun set(metric: Metric<*>) {
    val metricContent = metric.name
    val metricSplit = metricContent.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    var hystrixType = ""
    var serviceName = ""
    var methodName = ""
    var metricName = metricContent

    // format different types of hystrix metrics
    if (metricSplit[2] == "RibbonCommand") {
      hystrixType = "hystrix.HystrixCommand.RibbonCommand"
      serviceName = metricSplit[3]
      // remove prefix
      metricName = metricContent.substring(37)
    } else {
      if (metricSplit[1] == "HystrixCommand") {
        hystrixType = "hystrix.HystrixCommand"
        serviceName = metricSplit[2]
        methodName = metricSplit[3]
        metricName = metricContent.substring(23)
      }
      if (metricSplit[1] == "HystrixThreadPool") {
        hystrixType = "hystrix.HystrixThreadPool"
        serviceName = metricSplit[2]
        methodName = metricSplit[3]
        metricName = metricContent.substring(26)
      }
    }

    log.info("type=GAUGE, hystrix_type={}, service={}, method={}, name={}, value={}", hystrixType, serviceName,
        methodName, metricName, metric.value)
  }

  override fun increment(metric: Delta<*>) {
    log.info("type=COUNTER, name={}, count={}", metric.name, metric.value)
  }

  override fun reset(metricName: String) {
    // Not implemented
  }
}
