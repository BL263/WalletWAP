package it.walletwap.ewallet

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.impl.WalletServiceImpl
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.services.impl.TransactionServiceImpl
import it.walletwap.ewallet.services.impl.UserDetailsServiceImpl

@SpringBootApplication
class EWalletApplication :Extensions() {
    @Bean
    fun test(
        customerRepo: CustomerRepository,
        walletRepo: WalletRepository,
        transactionRepo: TransactionRepository,
        userRepo: UserRepository
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

            val user1= User(null,"martina1","pass",c3.email,true,Rolename.CUSTOMER.name)
            val c1Dto = c1.toDto()
            val c2Dto = c2.toDto()
            val c3Dto = c3.toDto()

            customerRepo.save(c1)
            customerRepo.save(c2)
            customerRepo.save(c3)
            customerRepo.save(c4)
            val walletService = WalletServiceImpl(walletRepo, customerRepo, transactionRepo)
            println(walletService.createWallet(c1Dto))
            println(walletService.createWallet(c2Dto))
            println(walletService.createWallet(c3Dto))
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
                    BigDecimal(2), formatter.parse("2021-03-15 12:00:00"), walletRepo.findById(3).get(),
                    walletRepo.findById(4).get()
                )
            )

            println(walletService.transactionsByDate(1, "1615590000000", "1616540400000"))
            val userService = UserDetailsServiceImpl(userRepo)
            userService.addRoleName(user1,Rolename.ADMIN.name)
            println(user1.roles)
            userService.removeRoleName(user1,Rolename.ADMIN.name)
            println(user1.roles)

            println(userService.getRoleName(user1))
        }
    }



}
fun main(args: Array<String>) {
    runApplication<EWalletApplication>(*args)
}
