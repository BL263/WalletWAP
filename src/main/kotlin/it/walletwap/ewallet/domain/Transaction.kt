package it.walletwap.ewallet.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    var id: Long? = null
    @Min(value = 0 )
    var amountTransferred: BigDecimal = BigDecimal.ZERO
    var transactionTime: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn
    var walletFrom: Wallet = Wallet()

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn
    var walletTo: Wallet = Wallet()
}