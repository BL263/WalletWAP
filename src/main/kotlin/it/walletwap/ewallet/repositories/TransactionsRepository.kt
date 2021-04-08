package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.Transactions
import it.walletwap.ewallet.domain.Wallet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface TransactionsRepository: CrudRepository<Transactions, Long> {

    fun findByWalletFromOrWalletTo(Walletfrom:Wallet,WalletTo:Wallet): List<Transactions?>?
}