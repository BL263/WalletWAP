package it.walletwap.ewallet.dto

import it.walletwap.ewallet.domain.Wallet

data class TransactionDto(var amountTransfered:Long, var walletFromId: Long, var walletToId: Long)

