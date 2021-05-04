package it.walletwap.ewallet.dto

import java.math.BigDecimal
import java.util.*

data class TransactionDTO(
    var amountTransferred: BigDecimal,
    var transactionTime: Date,
    var walletFromId: Long, var walletToId: Long
)

