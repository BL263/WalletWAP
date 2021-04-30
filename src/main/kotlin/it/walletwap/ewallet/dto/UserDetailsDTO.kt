package it.walletwap.ewallet.dto

import javax.validation.constraints.Email

data class UserDetailsDTO(
    var username: String?,
    @Email  //annotation used to validating email
    var email: String?,
    var isEnabled: Boolean = false,
    var role: String? = null,
    var password: String,
    var confirmPassword: String,
    var name: String,
    var surname: String,
    var address: String
)