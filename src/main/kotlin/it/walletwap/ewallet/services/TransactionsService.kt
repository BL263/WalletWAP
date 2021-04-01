package it.walletwap.ewallet.services

import it.walletwap.ewallet.domain.Transactions


interface TransactionsService {
    /*
	 * Queries the database and returns the TransactionDto corresponding to the transactionId passed as parameter.
	 * Throws an exception in case of invalid (negative) transactionId
	 * Returns null if no transaction is found with the given Id
	 */

    fun getTransactionById(transactionId: Long?): Transactions?

    /*
     * Stores the transaction passed as parameter in the application database
     */
    fun saveTransactions(transactionDto: Transactions?): Boolean

    /*
     * Returns an ArrayList with all the Transactions in the database.
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