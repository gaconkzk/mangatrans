package org.theflies.registry.web.rest.vm

/**
 *
 */
data class AuthResponseVM(val access_token:String, val refresh_token: String? = null)