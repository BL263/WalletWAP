package it.walletwap.ewallet

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.dto.WalletDTO
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Email

open class Extensions {


    fun Wallet.toDto(): WalletDTO = WalletDTO(this.id, this.amount)

    // TODO load name surname address from repository of customer
    fun User.toDto(): UserDetailsDTO = UserDetailsDTO(username, email,isEnabled,roles!!,password.toString(),password.toString(),"name","surname","address")

    fun List<Wallet>.toDto(): List<WalletDTO> = this.map { WalletDTO(it.id, it.amount) }

    fun Transaction.toDto(): TransactionDTO =
        TransactionDTO(this.amountTransferred,Date(), this.walletFrom.id ?: 0, this.walletTo.id ?: 0)

    fun List<Transaction?>?.toListDto(): List<TransactionDTO>? {
        return this?.map {
            TransactionDTO(it?.amountTransferred ?: BigDecimal.ZERO, Date(),it?.walletFrom?.id ?: -1, it?.walletTo?.id ?: -1)
        }
    }

    fun <T : Any> T?.toOptional(): Optional<T> = Optional.ofNullable(this)
}
enum class Rolename {
    CUSTOMER , ADMIN
}

//Currently located here, move it wherever you rather to
data class RegisterForm(
    var username: String,
    @Email
    var email: String,
    var name: String,
    var surname: String,
    var address: String,
    var password: String,
    var confirmPassword: String
)