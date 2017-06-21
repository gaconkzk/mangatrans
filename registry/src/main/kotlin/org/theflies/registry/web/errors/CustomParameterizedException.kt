package org.theflies.registry.web.errors

import java.util.*


/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:

 * <pre>
 * throw new CustomParameterizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
</pre> *

 * Can be translated with:

 * <pre>
 * "error.myCustomError" :  "The server says {{param0}} to {{param1}}"
</pre> *
 */
class CustomParameterizedException : RuntimeException {

  override val message: String

  private val paramMap = HashMap<String, String>()

  constructor(message: String, vararg params: String) : super(message) {
    this.message = message
    if (params.isNotEmpty()) {
      for (i in params.indices) {
        paramMap.put(PARAM + i, params[i])
      }
    }
  }

  constructor(message: String, paramMap: Map<String, String>) : super(message) {
    this.message = message
    this.paramMap.putAll(paramMap)
  }

  val errorVM: ParameterizedErrorVM
    get() = ParameterizedErrorVM(message, paramMap)

  companion object {

    private val serialVersionUID = 1L

    private val PARAM = "param"
  }
}
