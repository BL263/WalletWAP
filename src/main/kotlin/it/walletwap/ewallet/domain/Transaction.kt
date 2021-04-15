package it.walletwap.ewallet.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.*

@Entity
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var amountTransferred: Long = 0
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