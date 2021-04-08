package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Wallet
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

    override fun saveWallet(walletDto: WalletDto,walletId: Long,customerId: Long): Boolean {
        val wallet=Wallet()
        wallet.customerId=(customerRepository.findById(customerId).get() as Customer)
        wallet.amount=  walletDto.amount

        walletRepository?.save(wallet) == wallet
        (customerRepository.findById(customerId).get() as Customer).wallet.add(wallet)
        customerRepository.save ((customerRepository.findById(customerId).get() as Customer) )
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
}








