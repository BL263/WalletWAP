package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.Wallet
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : CrudRepository<Wallet, Long> {
    @Query(value = "select distinct id,amount from Wallet")
    fun getalllWallets(): List<Wallet>
}