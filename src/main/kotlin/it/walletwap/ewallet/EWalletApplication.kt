package it.walletwap.ewallet

import it.walletwap.ewallet.domain.*
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.impl.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableScheduling
import java.math.BigDecimal
import java.util.*
import javax.mail.MessagingException


@SpringBootApplication
@EnableScheduling
@Configuration
class EWalletApplication {
    @Value("\${spring.mail.host}")
    val mailHost: String? = null

    @Value("\${spring.mail.port}")
    val mailPort: Int? = null

    @Value("\${spring.mail.username}")
    val mailUsername: String? = null

    @Value("\${spring.mail.password}")
    val mailPassword: String? = null

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    val mailAuth: String? = null

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    val mailEnable: String? = null

    @Value("\${spring.mail.properties.mail.debug}")
    val mailDebug: String? = null

    @Bean
    fun sendMessage():SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setFrom(mailUsername.toString())
        return message
    }

    @Bean
    fun getJavaMailSender(): JavaMailSender? {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailHost
        mailSender.port = mailPort!!
        mailSender.username = mailUsername
        mailSender.password = mailPassword
        mailSender.protocol = "smtp"

        val javaMailProperties = Properties()
        javaMailProperties["mail.smtp.auth"] = mailAuth
        javaMailProperties["mail.smtp.starttls.enable"] = mailEnable
        mailSender.javaMailProperties = javaMailProperties
        try {
            mailSender.testConnection()
        } catch (e: MessagingException) {
            throw e
        }
        return mailSender
    }


    @Bean
    fun test(
        customerRepo: CustomerRepository,
        walletRepo: WalletRepository,
        transactionRepo: TransactionRepository,
        userRepo: UserRepository): CommandLineRunner {
        return CommandLineRunner {
            val customerService = CustomerServiceImpl(customerRepo)
            val walletService = WalletServiceImpl(walletRepo, customerRepo, transactionRepo)
            val transactionService = TransactionServiceImpl(transactionRepo, walletRepo)
            val userService = UserDetailsServiceImpl(userRepo, customerRepo)

            val c1 = Customer(name = "mirco", surname = "vigna", email = "mirco@gmail.com", deliveryAddress = "roveda 29")
            val c2 = Customer(name = "andrea", surname = "votte", email = "andrea@gmail.com", deliveryAddress = "roveda 29")
            val c3 = Customer(name = "martina", surname = "manci", email = "martina@gmail.com", deliveryAddress = "roveda 29")
            val c4 = Customer(name = "irene", surname = "malde", email = "irene@gmail.com", deliveryAddress = "roveda 29")

            customerRepo.save(c1)
            customerRepo.save(c2)
            customerRepo.save(c3)
            customerRepo.save(c4)

            println(walletService.createWallet(1))
            println(walletService.createWallet(2))
            println(walletService.createWallet(3))
            println(walletService.createWallet(4))
            val w1 = Wallet(amount = BigDecimal(100), customer = c3)
            walletRepo.save(w1)

            transactionService.saveTransactions(TransactionDTO(BigDecimal(55), Date(), 5, 2))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(15), Date(), 5, 1))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(5), Date(), 2, 4))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(10), Date(), 1, 5))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(3), Date(), 5, 3))
            println(walletService.getWalletTransactions(1))

        }
    }
}



fun main(args: Array<String>) {
    val context = runApplication<EWalletApplication>(*args)

}




