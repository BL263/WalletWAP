package it.walletwap.ewallet.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var amount: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    lateinit var customer: Customer

    @OneToMany(mappedBy = "walletFrom", fetch = FetchType.LAZY, targetEntity = Transaction::class)
    @JsonManagedReference
    lateinit var payees: MutableSet<Transaction>

    @OneToMany(mappedBy = "walletTo", fetch = FetchType.LAZY, targetEntity =  Transaction::class)
    @JsonManagedReference
    lateinit var payers: MutableSet<Transaction>
}