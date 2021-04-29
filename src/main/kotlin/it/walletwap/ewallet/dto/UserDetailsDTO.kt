package it.walletwap.ewallet.dto

data class UserDetailsDto(
    var username: String?,
    var email: String?,
    var isEnabled: Boolean?,
    var role: String
)

