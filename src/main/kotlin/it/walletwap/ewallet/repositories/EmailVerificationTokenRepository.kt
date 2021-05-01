package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.EmailVerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailVerificationTokenRepository:CrudRepository<EmailVerificationToken,Long> {
    fun findEmailVerificationTokenByToken(token: String): EmailVerificationToken?
    fun removeEmailVerificationTokenByToken(token: String)

}