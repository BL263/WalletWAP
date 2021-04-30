package it.walletwap.ewallet.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*

@Service
interface MailService {
     fun sendMessage ( toMail:String, subject:String, mailBody:String)
}