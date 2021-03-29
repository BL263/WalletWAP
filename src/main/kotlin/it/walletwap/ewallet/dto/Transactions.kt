package it.walletwap.ewallet.dto

import java.util.*
import javax.persistence.*

@Entity
class Transactions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null
    var customerId:Long?=null
    var amountAdded:Long?=null
    var amountReduced:Long?=null
    var transactiontime:Date?=null
    @OneToOne
    var walletFrom: Wallet?=null
    @OneToOne
    var walletTo: Wallet?=null
}