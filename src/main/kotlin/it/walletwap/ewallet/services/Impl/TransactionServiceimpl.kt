package it.walletwap.ewallet.services.Impl

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
class TransactionServiceimpl() : TransactionsService,Extensions() {
    @Autowired
    lateinit var repositoryTransaction: TransactionRepository
    @Autowired
    lateinit var repositoryWallet: WalletRepository
    override fun getTransactionById(transactionId: Long?): Optional<TransactionDto>? {
        if(transactionId!=null){
        var transaction=  repositoryTransaction.findById(transactionId)
        return if(transaction.isPresent) {
            (transaction.get().toDto()).toOptional()
        } else{
            null
        }
        }
            else return null
    }

    override fun saveTransactions(transactionDtoInput: TransactionDto): Boolean {
     val walletFrom=repositoryWallet.findById(transactionDtoInput.walletFromId)
        val walletTo=repositoryWallet.findById(transactionDtoInput.walletToId)
      return  if(walletFrom!= null && walletTo!= null && walletFrom.get().amount>= transactionDtoInput?.amountTransfered) {
            val transaction= Transaction()
            transaction.apply {
                this.amountTransfered=transactionDtoInput?.amountTransfered;
                this.walletFrom= walletFrom.get();
                this.walletTo= walletTo.get();
                this.transactionTime=Date()
            }
            repositoryTransaction.save(transaction)
          walletFrom.get().amount=walletFrom.get().amount-transactionDtoInput?.amountTransfered
          walletTo.get().amount=walletTo.get().amount+transactionDtoInput?.amountTransfered
            repositoryWallet.save(walletFrom.get())
            repositoryWallet.save(walletTo.get())
            return true
        }
        else false
    }


    override val allTransactions: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteTransactions(transactionId: Int?): Boolean? {
        TODO("Not yet implemented")
    }
}