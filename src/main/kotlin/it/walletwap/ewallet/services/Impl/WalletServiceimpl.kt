package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Customer
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class WalletServiceimpl(): WalletService ,Extensions(){
    @Autowired
    lateinit var walletRepository: WalletRepository
    @Autowired
    lateinit var customerRepository:CustomerRepository
    @Autowired
    lateinit var transactionRepository: TransactionRepository
    override fun getWalletById(walletId: Long): Optional<WalletDto>? {
        var wallet=  walletRepository.findById(walletId)
        return if(wallet.isPresent) {
            (wallet.get().toDto()).toOptional()
        } else{
            null
        }
    }

    override fun createWallet(customer: CustomerDto): Boolean {
        val wallet=Wallet()
       val customerId= customerRepository.findByEmail(customer.email)?.id?:-1
        wallet.customer_id=(customerRepository.findById(customerId).get() as Customer)
        wallet.amount= 0

        walletRepository?.save(wallet) == wallet
        (customerRepository.findById(customerId).get() as Customer).wallet.add(wallet)
        customerRepository.save ((customerRepository.findById(customerId).get() as Customer) )
        return true
    }
    override fun saveWallet(wallet:Wallet):Boolean{

        walletRepository.save(wallet)
        return true
    }

    override val allWallet: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteWallet(walletId: Long) {
       val walletfound=  (walletRepository.findById(walletId).get() as Wallet)
            walletRepository?.delete(walletfound)
    }

    override fun getAllWallets( ): List<WalletDto>? =
         walletRepository?.getalllWallets().toDto()

    override  fun  getWalletTransactions(walletId: Long): List<TransactionDto>? {
        var wallet = walletRepository.findById(walletId)
       return if(wallet!=null)
        transactionRepository?.findByWalletFromOrWalletTo(wallet.get(),wallet.get()).toListDto()
        else null
    }
    override  fun  getWalletTransaction(walletId: Long,transactionsId: Long): TransactionDto? {
        var wallet = walletRepository.findById(walletId)
        return if(wallet!=null)
            (transactionRepository?.findByWalletFromOrWalletTo(wallet.get(),wallet.get())?.find {it?.id== transactionsId })?.toDto()
        else null
    }
    override  fun  transactionsByDate(walletId: Long,startdate:String,endDate:String): List<TransactionDto?>? {
        var wallet = walletRepository.findById(walletId)


        // Create a DateFormatter object for displaying date in specified format.
        // Create a DateFormatter object for displaying date in specified format.
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val formatterdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        // Create a calendar object that will convert the date and time value in milliseconds to date.

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendarFrom = Calendar.getInstance()
        calendarFrom.timeInMillis = startdate.toLong()
        val datefrom= LocalDate.parse(formatter.format(calendarFrom.time),formatterdate)
        val calendarTo = Calendar.getInstance()
        calendarTo.timeInMillis = endDate.toLong()
        val dateto= LocalDate.parse(formatter.format(calendarTo.time),formatterdate)


        val startTime=Date.from(datefrom.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val endTime=Date.from(dateto.atStartOfDay(ZoneId.systemDefault()).toInstant())
        return if(wallet.isPresent)
            (transactionRepository?.findByWalletFromOrWalletTo(wallet.get(),wallet.get())?.filter { it?.transactionTime?.compareTo(endTime)!! <0 && it?.transactionTime?.compareTo(startTime)!!>0 }).toListDto()
        else null
    }
}










