package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.services.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class MailServiceImpl:MailService {

    @Autowired
    lateinit var mailSender: JavaMailSender
    @Autowired
    lateinit var simpleMessage:SimpleMailMessage

    override fun sendMessage ( toMail:String, subject:String, mailBody:String){
        simpleMessage.setText(mailBody)
        simpleMessage.setSubject(subject)
        simpleMessage.setTo(toMail)
        mailSender.send(simpleMessage)
    }



}