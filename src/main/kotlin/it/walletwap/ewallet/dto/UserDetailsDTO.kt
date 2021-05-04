package it.walletwap.ewallet.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.Email

class UserDetailsDTO(private var username: String,
                     @Email  //annotation used to validating email
                     var email: String,
                     private var isEnable: Boolean = false,
                     var roles: String? = null,
                     private var password: String,
                     var confirmPassword: String,
                     var name: String? = null,
                     var surname: String? = null,
                     var address: String? = null,
                     private val authorities: MutableList<GrantedAuthority> = mutableListOf()): UserDetails{

    override fun getAuthorities(): MutableList<out GrantedAuthority> {
        return this.authorities
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