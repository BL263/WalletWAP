package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.dto.Wallet
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.WalletService
import org.springframework.stereotype.Service

@Service
class WalletServiceimpl : WalletService {
    var walletRepository: WalletRepository? = null
    override fun getWalletById(walletId: Long?): Wallet? {
        TODO("Not yet implemented")
    }

    override fun saveWallet(wallet: Wallet?): Boolean {
        // walletRepository.save(wallet)
        return false
    }

    override val allWallet: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteWallet(walletId: Long?): Boolean? {
        TODO("Not yet implemented")
    }
}