package it.walletwap.ewallet.domain

import javax.persistence.*

@Entity
class Wallet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null

    var amount:Long=0


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    lateinit var customer_id: Customer

}