package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.dto.WalletDTO
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@Service
@Transactional
class WalletServiceImpl(val walletRepository:WalletRepository,val customerRepository:CustomerRepository,val transactionRepository :TransactionRepository) : WalletService, Extensions() {


    override fun getWalletById(walletId: Long): WalletDTO? {
        if (walletRepository.existsById(walletId)){
            return walletRepository.findById(walletId).get().toDto()
        } else {
            System.err.println("Wallet does not exist")
            return null
        }

    }

    override fun createWallet(customerId: Long): WalletDTO? {
        if (customerRepository.existsById(customerId)) {
            val owner: Customer = customerRepository.findById(customerId).get()
            val wallet = walletRepository.save(Wallet(customer = owner))
            return wallet.toDto()
        } else {
            System.err.println("Customer does not exist")
            return null
        }
    }

    override fun saveWallet(wallet: Wallet): Boolean {
        walletRepository.save(wallet)
        return true
    }

    override val allWallet: List<Wallet?>?
        get() = TODO("Not yet implemented")

    override fun deleteWallet(walletId: Long) {
        val walletFound = walletRepository.findById(walletId).get()
        walletRepository.delete(walletFound)
    }

    override fun getAllWallets(): List<WalletDTO>? =
        walletRepository.getAllWallets().toDto()

    override fun getWalletTransactions(walletId: Long): List<TransactionDTO>? {
        val wallet = walletRepository.findById(walletId)
        return transactionRepository.findByWalletFromOrWalletTo(wallet.get(), wallet.get()).toListDto()
    }

    override fun getWalletTransaction(walletId: Long, transactionsId: Long): TransactionDTO? {
        val wallet = walletRepository.findById(walletId)
        return if(wallet.isPresent)
         (transactionRepository.findByWalletFromOrWalletTo(wallet.get(), wallet.get())
            ?.find { it?.id == transactionsId })?.toDto()
        else {
            System.err.println("Wallet does not exist")
            return null
        }
    }

    override fun transactionsByDate(walletId: Long, startDate: String, endDate: String): List<TransactionDTO?>? {
        val wallet = walletRepository.findById(walletId)
        if(startDate.isNullOrEmpty())  return listOf<TransactionDTO>()
        if(endDate.isNullOrEmpty())  return listOf<TransactionDTO>()
        var startTime =Date()
        var endTime  = Date()
            try {
            // Create a DateFormatter object for displaying date in specified format.
            // Create a DateFormatter object for displaying date in specified format.
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            // Create a calendar object that will convert the date and time value in milliseconds to date.

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendarFrom = Calendar.getInstance()
            calendarFrom.timeInMillis = startDate.toLong()
            val dateFrom = LocalDate.parse(formatter.format(calendarFrom.time), formatterDate)
            val calendarTo = Calendar.getInstance()
            calendarTo.timeInMillis = endDate.toLong()
            val dateTo = LocalDate.parse(formatter.format(calendarTo.time), formatterDate)


             startTime = Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant())
             endTime  = Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant())
        }catch (e:Exception){
            System.err.println("Can not convert the millisecond date")
            return null
        }
        if(startTime==null) {
            System.err.println("invalid start date")
            return null
        }
        if(endTime==null) {
            System.err.println("invalid end date")
            return null
        }

        return if (wallet.isPresent)
            (transactionRepository.findByWalletFromOrWalletTo(wallet.get(), wallet.get())
                ?.filter { it?.transactionTime?.compareTo(endTime)!! < 0 && it.transactionTime?.compareTo(startTime)!! > 0 }).toListDto()
        else {
            System.err.println("Wallet not found")
            null
        }
    }
}










