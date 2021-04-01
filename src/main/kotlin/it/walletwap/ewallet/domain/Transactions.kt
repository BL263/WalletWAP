package it.walletwap.ewallet.domain

import java.util.*
import javax.persistence.*

@Entity
class Transactions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null
    var customerId:Long?=null
    var amountTransfered:Long?=null
    var transactionTime:Date?=null
    @OneToOne
    var walletFrom: Wallet?=null
    @OneToOne
    var walletTo: Wallet?=null
}