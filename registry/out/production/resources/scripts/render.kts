import org.springframework.web.servlet.view.script.RenderingContext
import org.springframework.context.support.ResourceBundleMessageSource
import javax.script.*
import org.springframework.beans.factory.getBean

// TODO Use engine.eval(String, Bindings) when https://youtrack.jetbrains.com/issue/KT-15450 will be fixed
fun render(template: String, model: Map<String, Any>, renderingContext: RenderingContext): String {
  val engine = ScriptEngineManager().getEngineByName("kotlin")
  val bindings = SimpleBindings(model)
  val messageSource = renderingContext.applicationContext.getBean<ResourceBundleMessageSource>()
  bindings.put("i18n", { code: String -> messageSource.getMessage(code, null, renderingContext.locale) })
  bindings.put("include", { path: String -> renderingContext.templateLoader.apply("templates/$path.kts") })
//  engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE)
  return engine.eval(template, bindings) as String
}