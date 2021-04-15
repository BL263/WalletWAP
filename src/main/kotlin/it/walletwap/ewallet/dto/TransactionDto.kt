package it.walletwap.ewallet.dto

data class TransactionDto(
    var amountTransferred: Long,
    var walletFromId: Long, var walletToId: Long
)

