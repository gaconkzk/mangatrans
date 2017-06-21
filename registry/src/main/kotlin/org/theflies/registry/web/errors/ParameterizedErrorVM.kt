package org.theflies.registry.web.errors

import java.io.Serializable

/**
 * View Model for sending a parameterized error message.
 */
class ParameterizedErrorVM(val message: String, val params: Map<String, String>) : Serializable {
  companion object {
    private const val serialVersionUID = 1L
  }
}
