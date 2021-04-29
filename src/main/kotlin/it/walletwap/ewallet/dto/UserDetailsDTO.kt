package it.walletwap.ewallet.dto

data class UserDetailsDTO(
    var username: String?,
    var email: String?,
    var isEnabled: Boolean?,
    var role: String
)

