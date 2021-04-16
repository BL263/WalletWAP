package it.walletwap.ewallet.domain

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    var id: Long? = null
    var name: String? = null
    var surname: String? = null
    var deliveryAddress: String? = null

    @Column(unique = true)
    var email: String = ""

    @OneToMany(
        mappedBy = "customer",
        targetEntity = Wallet::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    lateinit var wallet: MutableSet<Wallet>

}



