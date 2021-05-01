package it.walletwap.ewallet.services.impl
import it.walletwap.ewallet.domain.EmailVerificationToken
import it.walletwap.ewallet.repositories.EmailVerificationTokenRepository
import it.walletwap.ewallet.services.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import javax.transaction.Transactional

@Service
@Transactional
class NotificationServiceImpl: NotificationService {
    @Autowired
    lateinit var emailVerificationTokenRepository: EmailVerificationTokenRepository

    override fun createToken(username: String): String? {
        val uuid = UUID.randomUUID()

        val expiryDate= Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5))
        emailVerificationTokenRepository.save(EmailVerificationToken(null,expiryDate,uuid.toString(),username))
        println("created token: ${uuid} ")
         return  uuid.toString()
    }

    override fun checkToken(token: String): EmailVerificationToken? {
        return (emailVerificationTokenRepository.findEmailVerificationTokenByToken(token))
    }



}