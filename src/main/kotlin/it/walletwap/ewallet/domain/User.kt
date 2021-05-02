package it.walletwap.ewallet.domain

import it.walletwap.ewallet.dto.UserDetailsDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "User", indexes = [Index(name = "index", columnList = "username", unique = true)])
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    var id: Long? = null,
    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    var username: String, //(unique, indexed, nonempty, and validated),
    var password: String? = null,
    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    var email: String? = null, //   (unique and validated),
    var isEnabled: Boolean = false, //(set to false by default, which indicates whether the User is enabled
    // to perform operations in the Wallet Service),

    var roles: String? = null
) {
    fun toDto(): UserDetailsDTO = UserDetailsDTO(username, email,password.toString(),password.toString(),"name","surname","address")

}