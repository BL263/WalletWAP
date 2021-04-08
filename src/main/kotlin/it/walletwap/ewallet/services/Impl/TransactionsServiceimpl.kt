package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Transactions
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionsDto
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.TransactionsRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.TransactionsService
import org.aspectj.apache.bcel.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionsServiceimpl() : TransactionsService,Extensions() {
    @Autowired
    lateinit var repositoryTransaction: TransactionsRepository
    @Autowired
    lateinit var repositoryWallet: WalletRepository
    override fun getTransactionById(transactionId: Long?): Optional<TransactionsDto>? {
        if(transactionId!=null){
        var transaction=  repositoryTransaction.findById(transactionId)
        return if(!transaction.isEmpty) {
            (transaction.get().toDto()).toOptional()
        } else{
            null
        }
        }
            else return null
    }

    override fun saveTransactions(transactionDtoInput: TransactionsDto): Boolean {
     val walletFrom=repositoryWallet.findById(transactionDtoInput.walletFromId)
        val walletTo=repositoryWallet.findById(transactionDtoInput.walletToId)
      return  if(walletFrom!= null && walletTo!= null && walletFrom.get().amount>= transactionDtoInput?.amountTransfered) {
            val transaction= Transactions()
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