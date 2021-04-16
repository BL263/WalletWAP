package it.walletwap.ewallet.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    var id: Long? = null
    @Min(value= 0 )
    var amount: BigDecimal =  BigDecimal.ZERO

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