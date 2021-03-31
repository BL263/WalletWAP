package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.dto.Wallet
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class WalletServiceimpl(): WalletService {
    @Autowired
    lateinit var walletRepository: WalletRepository
    override fun getWalletById(walletId: Long): Optional<Wallet>? {
      
       return walletRepository?.findById(walletId)

    }

    override fun saveWallet(wallet: Wallet): Boolean {
        val walletsaved=  walletRepository?.save(wallet)
        return walletsaved==wallet
    }

    override val allWallet: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteWallet(walletId: Long) {
       val walletfound=  getWalletById(walletId)
        if(walletfound?.isPresent == true) {
            walletRepository?.delete(walletfound.get())
        }
    }

    override fun getAllWallets( ): List<Wallet>? {
        return walletRepository?.getalllWallets()
    }
}