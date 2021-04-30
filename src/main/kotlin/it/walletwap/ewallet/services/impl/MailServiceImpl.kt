package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.EWalletApplication
import it.walletwap.ewallet.services.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.StaticApplicationContext
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*

@Service
class MailServiceImpl:MailService {


    @Value("\${spring.mail.username}")
    val mailUsername: String? = null
    override fun sendMessage ( toMail:String, subject:String, mailBody:String){



        val parent:ApplicationContext = StaticApplicationContext()
   //   val mainclass=  parent.getBean(EWalletApplication::class.java)


        var message= SimpleMailMessage()
        message.setFrom(mailUsername.toString());
        message.setTo(toMail)
        message.setSubject(subject)
        message.setText(mailBody)
        message.setSentDate(Date())
       // sender?.send(message)

        
    }


}