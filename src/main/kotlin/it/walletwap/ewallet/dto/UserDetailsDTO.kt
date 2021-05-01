package it.walletwap.ewallet.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.Email

class UserDetailsDTO(var userName: String,
                          @Email  //annotation used to validating email
                          var email: String,
                          var isEnab: Boolean = false,
                          var roles: String? = null,
                          var pass: String,
                          var confirmPassword: String,
                          var name: String? = null,
                          var surname: String? = null,
                          var address: String? = null,
                          val authoritiesCollection: MutableList<GrantedAuthority> = mutableListOf()): UserDetails{

    override fun getAuthorities(): MutableList<out GrantedAuthority> {
        return this.authoritiesCollection
    }

    override fun getPassword(): String {
        return this.pass
    }

    override fun getUsername(): String {
        return this.userName
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        return this.isEnab
    }


}