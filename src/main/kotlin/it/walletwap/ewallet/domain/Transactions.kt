package it.walletwap.ewallet.domain

import java.util.*
import javax.persistence.*

@Entity
class Transactions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null
    var amountTransfered:Long=0
    var transactionTime:Date?=null
    @OneToOne
    var walletFrom: Wallet=Wallet()
    @OneToOne
    var walletTo: Wallet=Wallet()
}