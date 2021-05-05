package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.TransactionsService
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class TransactionServiceImpl(var transactionRepo: TransactionRepository, var walletRepo: WalletRepository): TransactionsService{

	override fun getTransactionById(transactionId: Long): TransactionDTO? {
		val transaction = transactionRepo.findById(transactionId)
		return if (transaction.isPresent) transaction.get().toDto()
		else null
	}

	override fun saveTransactions(transactionDTOInput: TransactionDTO): TransactionDTO? {

		val walletFrom=walletRepo.findById(transactionDTOInput.walletFromId)
		val walletTo=walletRepo.findById(transactionDTOInput.walletToId)
        if (walletFrom.isPresent && walletTo.isPresent) {
			return if (walletFrom.get().amount >= transactionDTOInput.amountTransferred) {
				val transaction = Transaction(null, transactionDTOInput.amountTransferred, Date(), walletFrom.get(),walletTo.get())
				transactionRepo.save(transaction)
				walletFrom.get().amount = walletFrom.get().amount - transactionDTOInput.amountTransferred
				walletTo.get().amount = walletTo.get().amount + transactionDTOInput.amountTransferred
				walletRepo.save(walletFrom.get())
				walletRepo.save(walletTo.get())
				transaction.toDto()
			} else {
				System.err.println("Payer does not have enough money")
				null
			}
        }else{
			System.err.println("One of referenced wallets does not exist")
			return null
		}
	}

}