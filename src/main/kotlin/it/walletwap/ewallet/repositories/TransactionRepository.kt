package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.Wallet
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {
    fun findByWalletFromOrWalletTo(walletFrom: Wallet, walletTo: Wallet): List<Transaction>?

    @Query(value = "SELECT * FROM transaction WHERE (wallet_from_id=?1 OR wallet_to_id=?1) AND transaction_time BETWEEN ?2 AND ?3",
        nativeQuery = true )
    fun findAllByOwnerRangeDate (owner: Long, fromDate: Date, toDate: Date): List<Transaction>
}