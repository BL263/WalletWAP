package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.services.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*

@Service
class MailServiceImpl:MailService {

    @Autowired
    private val applicationContext: ApplicationContext? = null
    @Value("\${spring.mail.username}")
    val mailUsername: String? = null
    override fun sendMessage ( toMail:String, subject:String, mailBody:String){
     //TODO send mail from service
    }



}