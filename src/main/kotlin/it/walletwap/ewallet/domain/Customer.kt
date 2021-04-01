package it.walletwap.ewallet.domain

import javax.persistence.*


@Entity
class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id:Long?=null
    var name:String?=null
    var sureName:String?=null
    var deliveryAddress:String?=null
    @Column(unique = true)
    var email:String?=null

    @OneToMany(mappedBy="id")
    private lateinit var wallet: MutableSet<Wallet>

}



