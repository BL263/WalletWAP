package it.walletwap.ewallet.dto

import java.math.BigDecimal

data class TransactionDto(
    var amountTransferred: BigDecimal,
    var walletFromId: Long, var walletToId: Long
)

