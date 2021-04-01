package it.walletwap.ewallet.dto

import it.walletwap.ewallet.domain.Wallet

data class TransactionsDto(var customerId:Long?, var amountTransfered:Long?,var walletFromId: Wallet?,var walletToId: Wallet?)

