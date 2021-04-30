package it.walletwap.ewallet.dto

data class UserDetailsDTO(
    var username: String?,
    var email: String?,
    var isEnabled: Boolean?,
    var role: String,
    var password: String,
    var  confirmPassword: String,
    var name: String,
    var surname: String,
    var address: String
)

