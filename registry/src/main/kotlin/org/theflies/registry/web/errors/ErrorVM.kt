package org.theflies.registry.web.errors

import java.io.Serializable

/**
 * View Model for transferring error message with a list of field errors.
 */
class ErrorVM : Serializable {

  val message: String
  val description: String?

  private var fieldErrors: MutableList<FieldErrorVM>? = null

  @JvmOverloads constructor(message: String, description: String? = null) {
    this.message = message
    this.description = description
  }

  constructor(message: String, description: String, fieldErrors: MutableList<FieldErrorVM>) {
    this.message = message
    this.description = description
    this.fieldErrors = fieldErrors
  }

  fun add(objectName: String, field: String, message: String) {
    if (fieldErrors == null) {
      fieldErrors = mutableListOf(FieldErrorVM(objectName, field, message))
    }
    fieldErrors!!.add(FieldErrorVM(objectName, field, message))
  }

  companion object {

    private const val serialVersionUID = 1L
  }
}
