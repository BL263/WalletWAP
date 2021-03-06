package it.walletwap.ewallet.services

import it.walletwap.ewallet.domain.EmailVerificationToken

interface NotificationService {

    fun createToken(username: String):String?
    fun checkToken(token: String): EmailVerificationToken?
    fun cleanTokens()

}