package it.walletwap.ewallet.controllers


import com.google.gson.Gson
import com.google.gson.JsonObject
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.dto.WalletDto
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.io.StringReader
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
    fun createTransactionByWalletId(@PathVariable walletId: Long,@RequestBody body:String): Boolean {
        val item: JsonObject = Gson().fromJson(body, JsonObject::class.java)
      return  if  (!item.get("amount").isJsonNull && !item.get("walletToId").isJsonNull ) {
            var transactionDto:TransactionDto = TransactionDto(item.get("amount").asBigDecimal,walletId ,item.get("walletToId").asLong )
            return transactionService.saveTransactions(transactionDto)
        }else false

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