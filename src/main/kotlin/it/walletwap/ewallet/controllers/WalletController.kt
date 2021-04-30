package it.walletwap.ewallet.controllers


import com.google.gson.Gson
import com.google.gson.JsonObject
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.TransactionDTO
import it.walletwap.ewallet.dto.WalletDTO
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/wallet")
class WalletController {

    @Autowired
    lateinit var walletService: WalletService

    @Autowired
    lateinit var transactionService: TransactionsService

    @PostMapping(path = ["/"], consumes = ["application/x-www-form-urlencoded"])
    @ResponseStatus(HttpStatus.CREATED)
    fun addWallet(customerId: Long): WalletDTO? {
         return walletService.createWallet(customerId)
    }

    @GetMapping("/{walletId}")
    @ResponseStatus(HttpStatus.OK)
    fun getWallet(@PathVariable walletId: Long): Optional<WalletDTO>? {
        return walletService.getWalletById(walletId)
    }

    @PostMapping("/{walletId}/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransactionByWalletId(@PathVariable walletId: Long,@RequestBody body:String): Boolean {
        val item: JsonObject = Gson().fromJson(body, JsonObject::class.java)
      return  if  (!item.get("amount").isJsonNull && !item.get("walletToId").isJsonNull ) {
            var transactionDTO:TransactionDTO = TransactionDTO(item.get("amount").asBigDecimal,Date(),walletId ,item.get("walletToId").asLong )
            return transactionService.saveTransactions(transactionDTO)
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
    ): List<TransactionDTO?>? {
        return walletService.transactionsByDate(walletId, dateFrom, dateTo)

    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    fun transactionByTransactionID(@PathVariable walletId: Long, @PathVariable transactionId: Long): TransactionDTO? {
        return walletService.getWalletTransaction(walletId, transactionId)
    }

}