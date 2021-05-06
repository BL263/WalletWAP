package it.walletwap.ewallet.domain

import it.walletwap.ewallet.dto.CustomerDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    var id: Long? = null,
    var name: String,
    var surname: String,
    var deliveryAddress: String,

    @Column(unique = true)
    @Email
    var email: String,

    @OneToMany(
        mappedBy = "customer",
        targetEntity = Wallet::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var wallet: MutableSet<Wallet> = mutableSetOf(),

) {
    fun toDto() = CustomerDTO(this.name, this.surname, this.deliveryAddress, this.email)
}



