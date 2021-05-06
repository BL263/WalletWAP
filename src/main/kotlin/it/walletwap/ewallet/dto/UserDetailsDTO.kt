package it.walletwap.ewallet.dto

import it.walletwap.ewallet.domain.Rolename
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.Email

class UserDetailsDTO(private var username: String,
                     @Email
                     var email: String,
                     private var isEnable: Boolean = false,
                     var roles: String = "",
                     private var password: String,
                     private val authorities: MutableList<GrantedAuthority> = mutableListOf()): UserDetails{

    override fun getAuthorities(): MutableList<out GrantedAuthority> {
     return (roles.split(" ").map { SimpleGrantedAuthority(it) }).toMutableList()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.isEnable
    }


}