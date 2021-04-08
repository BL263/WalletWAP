package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionsDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/wallet")
class WalletController {

    @Autowired
    lateinit var walletService: WalletService
    @Autowired
    lateinit var transactionService: TransactionsService

    @PostMapping("/")
    fun wallet(model: Model,  customer: CustomerDto ): String {
        //   model.addAttribute("amount",wallet.amount)
        return  walletService.createWallet(customer).toString()
    }

    @GetMapping("/{walletId}")
    fun wallet_id(@PathVariable walletId: Long)  : Optional<WalletDto>? {
            return  walletService.getWalletById(walletId)
    }

    @PostMapping("/{walletId}/transaction")
    fun transactionbyWalletId( walletIdFrom: Long, @PathVariable walletId: Long,amount: Long):  Boolean {
        return  transactionService.saveTransactions(TransactionsDto(amount,walletIdFrom,walletId))
    }

    @GetMapping("/{walletId}/transactions?from=<{dateFrom}>&to=<{dateTo}>")
    fun transactionByWalletIdandDate(
        @PathVariable walletId: Int,
        @PathVariable dateFrom: String,
        @PathVariable dateTo: String
    ): String {

        return "transactionsByDate"
    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    fun transactionByTransactionID(@PathVariable walletId: Long, @PathVariable transactionId: Long): TransactionsDto?{
        return walletService.getWalletTransaction(walletId,transactionId)
    }

}