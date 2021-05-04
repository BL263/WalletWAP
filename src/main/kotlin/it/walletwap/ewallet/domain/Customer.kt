package it.walletwap.ewallet.domain

import it.walletwap.ewallet.dto.CustomerDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "Customer")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    var id: Long? = null,
    var name: String? = null,
    var surname: String? = null,
    var deliveryAddress: String? = null,

    @Column(unique = true)
    var email: String = "",

    @OneToMany(
        mappedBy = "customer",
        targetEntity = Wallet::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var wallet: MutableSet<Wallet>? = null,
    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id")
    var user: User? = null
) {
    fun toDto() = CustomerDTO(this.name, this.surname, this.deliveryAddress, this.email)
}



