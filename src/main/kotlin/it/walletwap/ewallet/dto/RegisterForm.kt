package it.walletwap.ewallet.dto

import javax.validation.constraints.Email

data class RegisterForm(var username: String,
                   var name: String,
                   var surname: String,
                   @Email
                   var email: String,
                   var address: String,
                   var password: String,
                   var confirmPassword: String)