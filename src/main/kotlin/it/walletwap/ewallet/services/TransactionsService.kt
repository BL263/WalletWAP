package it.walletwap.ewallet.services

import it.walletwap.ewallet.dto.TransactionDTO


interface TransactionsService {
    /*
	 * Queries the database and returns the TransactionDTO corresponding to the transactionId passed as parameter.
	 * Throws an exception in case of invalid (negative) transactionId
	 * Returns null if no transaction is found with the given Id
	 */

    fun getTransactionById(transactionId: Long): TransactionDTO?

    /*
     * Stores the transaction passed as parameter in the application database
     */
    fun saveTransactions(transactionDTOInput: TransactionDTO): TransactionDTO?


}