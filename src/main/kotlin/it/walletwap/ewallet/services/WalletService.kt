package it.walletwap.ewallet.services

import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.TransactionsDto
import it.walletwap.ewallet.dto.WalletDto
import org.springframework.stereotype.Service
import java.util.*

@Service
interface WalletService {
    /*
    * Queries the database and returns the WalletDto corresponding to the walletId passed as parameter.
    * Throws an exception in case of invalid (negative) walletId
    * Returns null if no wallet is found with the given Id
    */

    fun getWalletById(walletId: Long): Optional<WalletDto>?

    /*
     * Stores the wallet passed as parameter in the application database
     */
    fun saveWallet(walletDto: WalletDto,walletId: Long,customerId: Long): Boolean

    /*
     * Returns an ArrayList with all the Wallet in the database.
     * Returns an empty ArrayList if no wallet is registered in the database
     */
    val allWallet: List<Any?>?

    /*
     * Deletes the wallet with the given Id from the database.
     * Throws an exception in case of invalid (negative) walletId
   	 * Returns true if the wallet is deleted, false otherwise
     */
    fun deleteWallet(walletId: Long)
    fun getAllWallets():List<WalletDto>?
    /* get transactions into and from a wallet */
    fun getWalletTransactions(walletId: Long): List<TransactionsDto>?
}