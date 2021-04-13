package it.walletwap.ewallet

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.dto.WalletDto
import java.util.*

open class Extensions {
    fun Customer.toDto(): CustomerDto =
         CustomerDto(this.name ,this.sureName,this.deliveryAddress,this.email)

   fun Wallet.toDto():WalletDto =
        WalletDto(this.id,this.amount)

    fun List<Wallet>.toDto():List<WalletDto> =
          this.map { WalletDto(it.id,it.amount) };


    fun Transaction.toDto(): TransactionDto =
         TransactionDto(this.amountTransfered ,this.walletFrom.id?:0,this.walletTo.id?:0)


    fun List<Transaction?>?.toListDto(): List<TransactionDto>? {
         return  this?.map {
             TransactionDto(it?.amountTransfered?:0,it?.walletFrom?.id?:-1,it?.walletTo?.id?:-1)
         }
    }



    fun <T : Any> T?.toOptional(): Optional<T> = Optional.ofNullable(this)



}