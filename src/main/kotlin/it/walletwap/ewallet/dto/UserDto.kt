package it.walletwap.ewallet.dto

data class UserDto(
    var username: String?,
    var email: String?,
    var isEnabled: Boolean?,
    var role: String
)
