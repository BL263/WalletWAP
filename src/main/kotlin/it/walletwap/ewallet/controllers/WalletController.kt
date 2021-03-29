package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.services.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallet")
class WalletController {

    @PostMapping("/")
    fun wallet(): String {

        return "index.html"
    }

    @GetMapping("/{walletId}")
    fun wallet_id(@PathVariable walletId: Int): String {

        return "getwalletById"
    }

    @PostMapping("/{walletId}/transaction")
    fun transactionbyWalletId(@PathVariable walletId: Int): String {

        return "transactionByWalletId"
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
    fun transactionByTransactionID(@PathVariable walletId: Int, @PathVariable transactionId: Int): String {

        return "transactionsById"
    }

}