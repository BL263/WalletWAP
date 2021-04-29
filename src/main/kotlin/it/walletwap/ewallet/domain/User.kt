package it.walletwap.ewallet.domain

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "User")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    var id: Long? = null
    @Column(name = "username",nullable = false)
    @NotBlank(message = "Name is mandatory")
    //ToDo fix index
  // @Index(name = "usenname_index", columnList = "username", unique = true)
     var username: String="" //(unique, indexed, nonempty, and validated),
    var password: String? = null
    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    var email: String? = null //   (unique and validated),
    var isEnabled: Boolean =false //(set to false by default, which indicates whether the User is enabled
                                   // to perform operations in the Wallet Service),

    var roles: String? = null

    fun getRoleName(){

    }
    fun addRoleName(){

    }
    fun removeRoleName(){

    }
}