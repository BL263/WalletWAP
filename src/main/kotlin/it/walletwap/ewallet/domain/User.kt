package it.walletwap.ewallet.domain

import it.walletwap.ewallet.dto.UserDetailsDTO
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.regex.Pattern
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(indexes = [Index(name = "index", columnList = "username", unique = true)])
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    var id: Long?= null,

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    var username: String,

    var password: String,

    @Column(unique = true)
    @Email
    var email: String,

    var isEnabled: Boolean = false,

    var roles: String = "",

    @OneToOne(targetEntity = Customer::class, cascade = [CascadeType.MERGE])
    var customer: Customer
) {
    fun toDto(): UserDetailsDTO{
        val authorities: MutableList<GrantedAuthority> = getRoles().map { SimpleGrantedAuthority(it.toString()) }.toMutableList()
        return UserDetailsDTO(username, email, isEnabled, roles, password, customer.id!!, authorities = authorities)
    }

    // Functions to get, add, remove roles
    fun getRoles(): Set<Rolename>{
        return roles.split(" ").map { Rolename.valueOf(it) }.toSet()
    }

    fun addRole(role: Rolename){
        roles = "$roles $role"
    }

    fun removeRole(role: Rolename){
        roles = roles.replace("$role", "").trim()
    }


}