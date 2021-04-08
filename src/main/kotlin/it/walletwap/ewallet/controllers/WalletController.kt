package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.TransactionsDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/wallet")
class WalletController {

    @Autowired
    lateinit var walletService: WalletService


    @PostMapping("/")
    fun wallet(): String {

        return "index.html"
    }

    @GetMapping("/{walletId}")
    fun wallet_id(@PathVariable walletId: Long)  : Optional<WalletDto>? {
            return  walletService.getWalletById(walletId)
    }

    @PostMapping("/{walletId}/transaction")
    fun transactionbyWalletId(@PathVariable walletId: Long): List<TransactionsDto>? {

        return  walletService.getWalletTransactions(walletId)
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