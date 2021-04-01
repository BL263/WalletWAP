package it.walletwap.ewallet.domain

import javax.persistence.*

@Entity
class Wallet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null

    var amount:Long=0
    @ManyToOne()
    lateinit var customerId: Customer

}