package it.walletwap.ewallet.domain

import it.walletwap.ewallet.Rolename
import it.walletwap.ewallet.dto.UserDetailsDto
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
    @NotBlank(message = "Email is mandatory")
    var email: String, //   (unique and validated),
    var isEnabled: Boolean = false, //(set to false by default, which indicates whether the User is enabled
    // to perform operations in the Wallet Service),

    var roles: String? = null
) {
    fun toDto(): UserDetailsDto{
        var authorities: MutableList<GrantedAuthority> = getRoles().map { SimpleGrantedAuthority(it.toString()) }.toMutableList()
        return UserDetailsDto(username, email ,isEnabled,roles!!,password.toString(),password.toString(), authoritiesCollection = authorities)
    }

    // Functions to get, add, remove roles
    fun getRoles(): Set<Rolename>{
        return roles!!.split(" ").map { Rolename.valueOf(it) }.toSet()
    }

    fun addRole(role: Rolename){
        roles.plus(" ").plus(role)
    }

    fun removeRole(role: Rolename){
        roles!!.replace("role", "")
    }


}