package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.TransactionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionServiceImpl : TransactionsService,Extensions() {
	@Autowired
	lateinit var repositoryTransaction: TransactionRepository
	@Autowired
	lateinit var repositoryWallet: WalletRepository
	override fun getTransactionById(transactionId: Long?): Optional<TransactionDto>? {
		return if(transactionId!=null){
			val transaction=  repositoryTransaction.findById(transactionId)
			if(transaction.isPresent) {
				(transaction.get().toDto()).toOptional()
			} else{
				null
			}
		} else null
	}

	override fun saveTransactions(transactionDtoInput: TransactionDto): Boolean {

		val walletFrom=repositoryWallet.findById(transactionDtoInput.walletFromId)
		val walletTo=repositoryWallet.findById(transactionDtoInput.walletToId)
        if (walletFrom.isPresent && walletTo.isPresent) {
            return if (walletFrom.get().amount >= transactionDtoInput.amountTransferred) {
                val transaction = Transaction()
                transaction.apply {
                    this.amountTransferred = transactionDtoInput.amountTransferred
                    this.walletFrom = walletFrom.get()
                    this.walletTo = walletTo.get()
                    this.transactionTime = Date()
                }
                repositoryTransaction.save(transaction)
                walletFrom.get().amount = walletFrom.get().amount - transactionDtoInput.amountTransferred
                walletTo.get().amount = walletTo.get().amount + transactionDtoInput.amountTransferred
                repositoryWallet.save(walletFrom.get())
                repositoryWallet.save(walletTo.get())
                return true
            } else {
                System.err.println("Payer does not have enough money")
                return false
            }
        }else{
			System.err.println("One of referenced wallets does not exist")
			return false
		}
	}

	override val allTransactions: List<Any?>?
		get() = TODO("Not yet implemented")

	override fun deleteTransactions(transactionId: Int?): Boolean? {
		TODO("Not yet implemented")
	}
}