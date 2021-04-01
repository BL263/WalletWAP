package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.domain.Transactions
import it.walletwap.ewallet.services.TransactionsService
import org.springframework.stereotype.Service

@Service
class TransactionsServiceimpl : TransactionsService {
    override fun getTransactionById(transactionId: Long?): Transactions? {
        TODO("Not yet implemented")
    }

    override fun saveTransactions(transactionDto: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override val allTransactions: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteTransactions(transactionId: Int?): Boolean? {
        TODO("Not yet implemented")
    }
}