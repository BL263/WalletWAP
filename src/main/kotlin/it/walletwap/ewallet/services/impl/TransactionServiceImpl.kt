package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.TransactionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class TransactionServiceImpl(val repositoryTransaction: TransactionRepository,val repositoryWallet: WalletRepository) : TransactionsService,Extensions() {

	override fun getTransactionById(transactionId: Long?): Optional<TransactionDTO>? {
		return if(transactionId!=null){
			val transaction=  repositoryTransaction.findById(transactionId)
			if(transaction.isPresent) {
				(transaction.get().toDto()).toOptional()
			} else{
				null
			}
		} else null
	}

	override fun saveTransactions(transactionDTOInput: TransactionDTO): Boolean {

		val walletFrom=repositoryWallet.findById(transactionDTOInput.walletFromId)
		val walletTo=repositoryWallet.findById(transactionDTOInput.walletToId)
        if (walletFrom.isPresent && walletTo.isPresent) {
            return if (walletFrom.get().amount >= transactionDTOInput.amountTransferred) {
                val transaction = Transaction(null,transactionDTOInput.amountTransferred,Date(),walletFrom.get(),walletTo.get())
                repositoryTransaction.save(transaction)
                walletFrom.get().amount = walletFrom.get().amount - transactionDTOInput.amountTransferred
                walletTo.get().amount = walletTo.get().amount + transactionDTOInput.amountTransferred
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