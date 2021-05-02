package it.walletwap.ewallet.services

import it.walletwap.ewallet.domain.EmailVerificationToken

interface NotificationService {

    fun createToken(string: String):String?
    fun checkToken(token: String): EmailVerificationToken?
    fun cleanTokens()

}