package it.walletwap.ewallet

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.impl.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import javax.mail.MessagingException


@SpringBootApplication
@Configuration
class EWalletApplication : Extensions() {
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


    fun sendMessage(toMail: String, subject: String, mailBody: String) {
        var message = SimpleMailMessage()
        message.setFrom(mailUsername.toString());
        message.setTo(toMail)
        message.setSubject(subject)
        message.setText(mailBody)
        message.setSentDate(Date())
        getJavaMailSender()?.send(message)

    }


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
        transactionRepo: TransactionRepository, userRepo: UserRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            val c1 =
                Customer(name = "mirco", surname = "vigna", email = "mirco@gmail.com", deliveryAddress = "roveda 29")
            val c2 = Customer(
                name = "andrea",
                surname = "vottero",
                email = "andrea@gmail.com",
                deliveryAddress = "roveda 29"
            )
            val c3 = Customer(
                name = "martina",
                surname = "mancinelli",
                email = "martina@gmail.com",
                deliveryAddress = "roveda 29"
            )
            val c4 =
                Customer(name = "irene", surname = "maldera", email = "irene@gmail.com", deliveryAddress = "roveda 29")
            val user1 = User(null, "behnam2", "pass", "behnam263@gmail.com", true, Rolename.CUSTOMER.name)
            val c1Dto = c1.toDto()
            val c2Dto = c2.toDto()
            val c3Dto = c3.toDto()

            customerRepo.save(c1)
            customerRepo.save(c2)
            customerRepo.save(c3)
            customerRepo.save(c4)
            val walletService = WalletServiceImpl(walletRepo, customerRepo, transactionRepo)
            println(walletService.createWallet(1))
            println(walletService.createWallet(2))
            println(walletService.createWallet(3))
            val w1 = Wallet(amount = BigDecimal(100), customer = c3)
            walletRepo.save(w1)
            val transactionService = TransactionServiceImpl(transactionRepo, walletRepo)
            transactionService.saveTransactions(TransactionDTO(amountTransferred = BigDecimal(55), Date(), 1, 2))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(15), Date(), 1, 2))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(5), Date(), 1, 2))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(10), Date(), 1, 2))
            transactionService.saveTransactions(TransactionDTO(BigDecimal(20), Date(), 1, 2))
            println(walletService.getWalletTransactions(1))
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY)
            transactionRepo.save(
                Transaction(
                    null,
                    BigDecimal(17), formatter.parse("2021-03-12 12:00:00"), walletRepo.findById(4).get(),
                    walletRepo.findById(3).get()
                )
            )

            transactionRepo.save(
                Transaction(
                    null,
                    BigDecimal(3), formatter.parse("2021-03-21 12:00:00"), walletRepo.findById(4).get(),
                    walletRepo.findById(3).get()
                )
            )

            transactionRepo.save(
                Transaction(
                    null,
                    BigDecimal(2),
                    formatter.parse("2021-03-15 12:00:00"),
                    walletRepo.findById(3).get(),
                    walletRepo.findById(4).get()
                )
            )

            println(walletService.transactionsByDate(1, "1615590000000", "1616540400000"))
            val userService = UserDetailsServiceImpl(userRepo, customerRepo)
            userService.addRoleName(user1, Rolename.ADMIN.name)
            println(user1.roles)
            userService.removeRoleName(user1, Rolename.ADMIN.name)
            println(user1.roles)

            println(userService.getRoleName(user1))
            userService.registerUser(user1.toDto())
            println(userService.getuserByUserName(user1.username)?.email)

            //TODO inside bean it is difficult to access application context
            sendMessage(user1.email.toString(), "Testing mail sender", "Hi this is a test for mail sender")

        }
    }


}



fun main(args: Array<String>) {
    val context = runApplication<EWalletApplication>(*args)

}




