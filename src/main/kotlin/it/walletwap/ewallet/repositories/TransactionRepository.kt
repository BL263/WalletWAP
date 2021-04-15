package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.Wallet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {
    fun findByWalletFromOrWalletTo(walletFrom: Wallet, walletTo: Wallet): List<Transaction?>?
}