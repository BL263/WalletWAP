package it.walletwap.ewallet.services.impl
import it.walletwap.ewallet.services.NotificationService
import org.jboss.jandex.Main
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class NotificationServiceImpl: NotificationService {
    override fun createUUID(): String? {
        val uuid = UUID.randomUUID()
         return  uuid.toString()
    }


}