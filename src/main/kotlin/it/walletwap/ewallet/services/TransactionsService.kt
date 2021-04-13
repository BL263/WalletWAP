package it.walletwap.ewallet.services

import it.walletwap.ewallet.dto.TransactionDto
import java.util.*


interface TransactionsService {
    /*
	 * Queries the database and returns the TransactionDto corresponding to the transactionId passed as parameter.
	 * Throws an exception in case of invalid (negative) transactionId
	 * Returns null if no transaction is found with the given Id
	 */

    fun getTransactionById(transactionId: Long?): Optional<TransactionDto>?

    /*
     * Stores the transaction passed as parameter in the application database
     */
    fun saveTransactions(transactionDtoInput: TransactionDto): Boolean

    /*
     * Returns an ArrayList with all the Transaction in the database.
     * Returns an empty ArrayList if no transaction is registered in the database
     */
    val allTransactions: List<Any?>?

    /*
     * Deletes the transaction with the given Id from the database.
     * Throws an exception in case of invalid (negative) transactionId
   	 * Returns true if the transaction is deleted, false otherwise
     */
    fun deleteTransactions(transactionId: Int?): Boolean?

}