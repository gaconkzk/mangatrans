package org.theflies.registry.web.rest.vm

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 *
 */
/**
 * View Model object for representing a user's credentials
 */
data class LoginVM(
    @field:Size(min=1, max=50)
    @field:NotNull
    var username: String? = null,
    @field:NotNull
    var password: String? = null,
    val isRememberMe: Boolean? = null) {
  override fun toString(): String {
    return "LoginVM{" +
        "username='" + username + '\'' +
        ", rememberMe=" + isRememberMe +
        '}'
  }
}
