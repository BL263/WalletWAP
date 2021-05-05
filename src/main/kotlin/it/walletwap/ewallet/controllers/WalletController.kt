package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.dto.TransactionForm
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @PostMapping(path = ["/"])
    @ResponseStatus(HttpStatus.CREATED)
    fun addWallet(@RequestParam customerId: Long): ResponseEntity<Any> {
        val wallet = walletService.createWallet(customerId)
        return if(wallet != null){
            ResponseEntity.ok(wallet)
        } else ResponseEntity.badRequest().body("Bad Request Message")

    }

    @GetMapping("/{walletId}")
    @ResponseStatus(HttpStatus.OK)
    fun getWallet(@PathVariable walletId: Long): ResponseEntity<Any> {
        val wallet = walletService.getWalletById(walletId)
        return if(wallet!=null){
            ResponseEntity.ok(wallet)
        } else ResponseEntity.badRequest().body("Bad Request Message")
    }

    @PostMapping("/{walletId}/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransactionByWalletId(@PathVariable walletId: Long,@RequestBody transactionForm: TransactionForm): ResponseEntity<Any> {
        val transactionDTO = transactionService.saveTransactions(TransactionDTO(BigDecimal(transactionForm.amount),Date(),walletId ,transactionForm.walletToId))
        return if (transactionDTO!=null){
            ResponseEntity.ok(transactionDTO)
        } else ResponseEntity.badRequest().body("Bad Request Message")
    }

    @GetMapping("/{walletId}/transactions")
    @ResponseStatus(HttpStatus.OK)
    fun transactionByWalletIdAndDate(
        @PathVariable walletId: Long,
        @RequestParam(
            name = "from",
            required = false,
            defaultValue = "1617975717827"
        ) dateFrom: Long,
        @RequestParam(
            name = "to",
            required = false,
            defaultValue = "1617975717827"
        ) dateTo: Long
    ): ResponseEntity<Any> {
        val list = walletService.transactionsByDate(walletId, dateFrom, dateTo)
        return if (list!=null){
            ResponseEntity.ok(list)
        } else ResponseEntity.badRequest().body("Bad Request Message")


    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    fun transactionByTransactionID(@PathVariable walletId: Long, @PathVariable transactionId: Long): ResponseEntity<Any> {
        val transaction = walletService.getWalletTransaction(walletId, transactionId)
        return if (transaction!=null){
            ResponseEntity.ok(transaction)
        } else ResponseEntity.badRequest().body("Bad Request Message")
    }

}