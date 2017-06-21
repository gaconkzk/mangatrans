package org.theflies.registry.config.locale

import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.TimeZoneAwareLocaleContext
import org.springframework.util.StringUtils
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.util.WebUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Angular cookie saved the locale with a double quote (%22en%22).
 * So the default CookieLocaleResolver#StringUtils.parseLocaleString(localePart)
 * is not able to parse the locale.

 * This class will check if a double quote has been added, if so it will remove it.
 */
class VueCookieLocaleResolver : CookieLocaleResolver() {

  override fun resolveLocale(request: HttpServletRequest): Locale {
    parseLocaleCookieIfNecessary(request)
    return request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME) as Locale
  }

  override fun resolveLocaleContext(request: HttpServletRequest): LocaleContext {
    parseLocaleCookieIfNecessary(request)
    return object : TimeZoneAwareLocaleContext {
      override fun getLocale(): Locale {
        return request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME) as Locale
      }

      override fun getTimeZone(): TimeZone {
        val tz = request.getAttribute(CookieLocaleResolver.TIME_ZONE_REQUEST_ATTRIBUTE_NAME)
        return if (tz != null) tz as TimeZone else TimeZone.getDefault()
      }
    }
  }

  override fun addCookie(response: HttpServletResponse, cookieValue: String) {
    // Mandatory cookie modification for AngularJS to support the locale switching on the server side.
    super.addCookie(response, "%22$cookieValue%22")
  }

  private fun parseLocaleCookieIfNecessary(request: HttpServletRequest) {
    if (request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME) == null) {
      // Retrieve and parse cookie value.
      val cookie = WebUtils.getCookie(request, cookieName)
      var locale: Locale? = null
      var timeZone: TimeZone? = null
      if (cookie != null) {
        var value = cookie.value

        // Remove the double quote
        value = StringUtils.replace(value, "%22", "")

        var localePart = value
        var timeZonePart: String? = null
        val spaceIndex = localePart.indexOf(' ')
        if (spaceIndex != -1) {
          localePart = value.substring(0, spaceIndex)
          timeZonePart = value.substring(spaceIndex + 1)
        }
        locale = if ("-" != localePart) StringUtils.parseLocaleString(localePart.replace('-', '_')) else null
        if (timeZonePart != null) {
          timeZone = StringUtils.parseTimeZoneString(timeZonePart)
        }
        if (logger.isTraceEnabled) {
          logger.trace("Parsed cookie value [" + cookie.value + "] into locale '" + locale +
              "'" + if (timeZone != null) " and time zone '" + timeZone.id + "'" else "")
        }
      }
      request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME,
          if (locale != null) locale else determineDefaultLocale(request))

      request.setAttribute(CookieLocaleResolver.TIME_ZONE_REQUEST_ATTRIBUTE_NAME,
          if (timeZone != null) timeZone else determineDefaultTimeZone(request))
    }
  }
}
