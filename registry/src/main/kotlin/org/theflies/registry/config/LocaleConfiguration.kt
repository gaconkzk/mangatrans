package org.theflies.registry.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.theflies.registry.config.locale.VueCookieLocaleResolver

@Configuration
class LocaleConfiguration : WebMvcConfigurer {

  @Bean(name = arrayOf("localeResolver"))
  fun localeResolver(): LocaleResolver {
    val cookieLocaleResolver = VueCookieLocaleResolver()
    cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY")
    return cookieLocaleResolver
  }

  override fun addInterceptors(registry: InterceptorRegistry?) {
    val localeChangeInterceptor = LocaleChangeInterceptor()
    localeChangeInterceptor.paramName = "language"
    registry!!.addInterceptor(localeChangeInterceptor)
  }
}
