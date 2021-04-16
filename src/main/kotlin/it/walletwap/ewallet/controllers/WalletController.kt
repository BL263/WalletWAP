package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*


@RestController
@RequestMapping("/wallet")
class WalletController {

    @Autowired
    lateinit var walletService: WalletService

    @Autowired
    lateinit var transactionService: TransactionsService

    @PostMapping("/")
    fun wallet(model: Model, customer: CustomerDto=CustomerDto("","","","")): String {
        //   model.addAttribute("amount",wallet.amount)
        return  if(customer.email!=null)
         walletService.createWallet(customer).toString()
        else  "Problem in parsing input values"
    }

    @GetMapping("/{walletId}")
    fun walletId(@PathVariable walletId: Long): Optional<WalletDto>? {
        return walletService.getWalletById(walletId)
    }

    @PostMapping("/{walletId}/transaction")
    fun transactionByWalletId(walletIdFrom: Long, @PathVariable walletId: Long, amount: BigDecimal): Boolean {
        return transactionService.saveTransactions(TransactionDto(amount, walletIdFrom, walletId))
    }

    @GetMapping("/{walletId}/transactions")
    fun transactionByWalletIdAndDate(
        @PathVariable walletId: Long,
        @RequestParam(
            name = "from",
            required = false,
            defaultValue = "1617975717827"
        ) dateFrom: String,
        @RequestParam(
            name = "to",
            required = false,
            defaultValue = "1617975717827"
        ) dateTo: String
    ): List<TransactionDto?>? {
        return walletService.transactionsByDate(walletId, dateFrom, dateTo)

    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    fun transactionByTransactionID(@PathVariable walletId: Long, @PathVariable transactionId: Long): TransactionDto? {
        return walletService.getWalletTransaction(walletId, transactionId)
    }

}