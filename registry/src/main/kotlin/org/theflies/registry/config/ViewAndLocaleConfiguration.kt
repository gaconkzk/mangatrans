package org.theflies.registry.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver
import org.theflies.registry.config.locale.VueCookieLocaleResolver

@Configuration
class ViewAndLocaleConfiguration : WebMvcConfigurer {

  @Bean
  fun kotlinScriptConfigurer(): ScriptTemplateConfigurer {
    val configurer = ScriptTemplateConfigurer()
    configurer.engineName = "kotlin"
    configurer.setScripts("scripts/render.kts")
    configurer.renderFunction = "render"
    configurer.isSharedEngine = false
    return configurer
  }

  @Bean
  fun kotlinScriptViewResolver(): ViewResolver {
    val viewResolver = ScriptTemplateViewResolver()
    viewResolver.setPrefix("templates/")
    viewResolver.setSuffix(".kts")
    return viewResolver
  }

  @Bean(name = arrayOf("localeResolver"))
  fun localeResolver(): LocaleResolver {
    val cookieLocaleResolver = VueCookieLocaleResolver()
    cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY")
    return cookieLocaleResolver
  }

  override fun addInterceptors(registry: InterceptorRegistry?) {
    val lci = LocaleChangeInterceptor()
    lci.paramName = "language"
    registry!!.addInterceptor(lci)
  }
}
