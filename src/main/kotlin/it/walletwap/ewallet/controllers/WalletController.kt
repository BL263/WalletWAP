package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/wallet")
class WalletController {

    @Autowired
    lateinit var walletService: WalletService

    @Autowired
    lateinit var transactionService: TransactionsService

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addWallet(model: Model,@RequestBody @Valid customer: CustomerDto=CustomerDto("","","","")): String {
        //   model.addAttribute("amount",wallet.amount)
        return  if(customer.email!=null)
         walletService.createWallet(customer).toString()
        else  "Problem in parsing input values"
    }

    @GetMapping("/{walletId}")
    @ResponseStatus(HttpStatus.OK)
    fun getWallet(@PathVariable walletId: Long): Optional<WalletDto>? {
        return walletService.getWalletById(walletId)
    }

    @PostMapping("/{walletId}/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransactionByWalletId(walletIdFrom: Long, @PathVariable walletId: Long, amount: BigDecimal): Boolean {
        return transactionService.saveTransactions(TransactionDto(amount, walletIdFrom, walletId))
    }

    @GetMapping("/{walletId}/transactions")
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    fun transactionByTransactionID(@PathVariable walletId: Long, @PathVariable transactionId: Long): TransactionDto? {
        return walletService.getWalletTransaction(walletId, transactionId)
    }

}