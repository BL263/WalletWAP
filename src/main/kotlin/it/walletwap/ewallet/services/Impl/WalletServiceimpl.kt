package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionsDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionsRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class WalletServiceimpl(): WalletService ,Extensions(){
    @Autowired
    lateinit var walletRepository: WalletRepository
    @Autowired
    lateinit var customerRepository:CustomerRepository
    @Autowired
    lateinit var transactionRepository: TransactionsRepository
    override fun getWalletById(walletId: Long): Optional<WalletDto>? {
        var wallet=  walletRepository.findById(walletId)
        return if(!wallet.isEmpty) {
            (wallet.get().toDto()).toOptional()
        } else{
            null
        }
    }

    override fun createWallet(customer: CustomerDto): Boolean {
        val wallet=Wallet()
       val customerId= customerRepository.findByEmail(customer.email)?.id?:-1
        wallet.customerId=(customerRepository.findById(customerId).get() as Customer)
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

    override  fun  getWalletTransactions(walletId: Long): List<TransactionsDto>? {
        var wallet = walletRepository.findById(walletId)
       return if(wallet!=null)
        transactionRepository?.findByWalletFromOrWalletTo(wallet.get(),wallet.get()).toListDto()
        else null
    }
    override  fun  getWalletTransaction(walletId: Long,transactionsId: Long): TransactionsDto? {
        var wallet = walletRepository.findById(walletId)
        return if(wallet!=null)
            (transactionRepository?.findByWalletFromOrWalletTo(wallet.get(),wallet.get())?.find {it?.id== transactionsId })?.toDto()
        else null
    }
}








