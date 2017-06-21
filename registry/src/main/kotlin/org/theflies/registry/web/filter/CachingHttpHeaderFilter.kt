package org.theflies.registry.web.filter

import org.theflies.registry.config.TheFliesProperties
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

/**
 * This filter is used in production, to put HTTP cache headers with a long (1 month) expiration time.
 */
class CachingHttpHeadersFilter(private val fliesProperties: TheFliesProperties) : Filter {

  private var cacheTimeToLive = TimeUnit.DAYS.toMillis(1461L)

  @Throws(ServletException::class)
  override fun init(filterConfig: FilterConfig) {
    cacheTimeToLive = TimeUnit.DAYS.toMillis(fliesProperties.http.cache.timeToLiveInDays)
  }

  override fun destroy() {
    // Nothing to destroy
  }

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

    val httpResponse = response as HttpServletResponse

    httpResponse.setHeader("Cache-Control", "max-age=$cacheTimeToLive, public")
    httpResponse.setHeader("Pragma", "cache")

    // Setting Expires header, for proxy caching
    httpResponse.setDateHeader("Expires", cacheTimeToLive + System.currentTimeMillis())

    // Setting the Last-Modified header, for browser caching
    httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED)

    chain.doFilter(request, response)
  }

  companion object {

    // We consider the last modified date is the start up time of the server
    private val LAST_MODIFIED = System.currentTimeMillis()
  }
}
