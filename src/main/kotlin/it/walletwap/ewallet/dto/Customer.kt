package it.walletwap.ewallet.dto

import javax.persistence.*


@Entity
class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null
    var name:String?=null
    var sureName:String?=null
    var deliveryAddress:String?=null
    var email:String?=null

    @ManyToOne
    var customer: Wallet? = null
}



