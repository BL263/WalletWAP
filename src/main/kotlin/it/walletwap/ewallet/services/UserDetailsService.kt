package it.walletwap.ewallet.services


import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.RegisterForm
import it.walletwap.ewallet.dto.UserDetailsDTO


interface UserDetailsService: org.springframework.security.core.userdetails.UserDetailsService {

    /*
	 * Queries the database and returns the userDto corresponding to the userId passed as parameter.
	 * Throws an exception in case of invalid (negative) userId
	 * Returns null if no user is found with the given Id
	 */

    fun getUserByUserName(username: String): User?

    /*
     * Stores the user passed as parameter in the application database
     */
    fun registerUser(registerForm: RegisterForm): UserDetailsDTO?

    fun toggleIsEnableUser(user: User, isEnable: Boolean): Boolean?

    override fun loadUserByUsername(username: String): UserDetailsDTO

    fun verifyToken(token: String):String?


}