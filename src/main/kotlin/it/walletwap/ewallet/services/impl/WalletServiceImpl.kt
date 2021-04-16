package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class WalletServiceImpl : WalletService, Extensions() {
    @Autowired
    lateinit var walletRepository: WalletRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var transactionRepository: TransactionRepository
    override fun getWalletById(walletId: Long): Optional<WalletDto>? {
        val wallet = walletRepository.findById(walletId)
        return if (wallet.isPresent) (wallet.get().toDto()).toOptional() else null
    }

    override fun createWallet(customer: CustomerDto): Boolean {
        if(customer.email==null)return false
        val customerId = customerRepository.findByEmail(customer.email!!)?.id ?: -1
        return if(customerId>=0)
        {
            val wallet = Wallet().also {
                it.customer=customerRepository.findById(customerId).get();
                it.amount=BigDecimal.ZERO;
                it.payees=mutableSetOf<Transaction>();
                it.payers=mutableSetOf<Transaction>();
            }
            walletRepository.save(wallet)
            customerRepository.findById(customerId).get().wallet.add(wallet)
            customerRepository.save((customerRepository.findById(customerId).get()))
        return true
        }
        else false
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

    override fun getAllWallets(): List<WalletDto>? =
        walletRepository.getAllWallets().toDto()

    override fun getWalletTransactions(walletId: Long): List<TransactionDto>? {
        val wallet = walletRepository.findById(walletId)
        return transactionRepository.findByWalletFromOrWalletTo(wallet.get(), wallet.get()).toListDto()
    }

    override fun getWalletTransaction(walletId: Long, transactionsId: Long): TransactionDto? {
        val wallet = walletRepository.findById(walletId)
        return (transactionRepository.findByWalletFromOrWalletTo(wallet.get(), wallet.get())
            ?.find { it?.id == transactionsId })?.toDto()
    }

    override fun transactionsByDate(walletId: Long, startDate: String, endDate: String): List<TransactionDto?>? {
        val wallet = walletRepository.findById(walletId)


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


        val startTime = Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val endTime = Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant())
        return if (wallet.isPresent)
            (transactionRepository.findByWalletFromOrWalletTo(wallet.get(), wallet.get())
                ?.filter { it?.transactionTime?.compareTo(endTime)!! < 0 && it.transactionTime?.compareTo(startTime)!! > 0 }).toListDto()
        else null
    }
}










