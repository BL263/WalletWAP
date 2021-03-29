package it.walletwap.ewallet.dto

import javax.persistence.*

@Entity
class Wallet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null
    var amount:Double?=null

}