package it.walletwap.ewallet.dto

import it.walletwap.ewallet.domain.Wallet

data class TransactionsDto( var amountTransfered:Long?,var walletFromId: Long,var walletToId: Long)

