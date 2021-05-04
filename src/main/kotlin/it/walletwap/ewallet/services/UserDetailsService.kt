package it.walletwap.ewallet.services


import it.walletwap.ewallet.domain.User
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
    fun registerUser(user: UserDetailsDTO): UserDetailsDTO?

    /*
     * Returns an ArrayList with all the users in the database.
     * Returns an empty ArrayList if no user is registered in the database
     */
    val allusers: List<Any?>?

    /*
     * Deletes the user with the given Id from the database.
     * Throws an exception in case of invalid (negative) userId
   	 * Returns true if the user is deleted, false otherwise
     */
    fun deleteUser(userId: Int?): Boolean?

    fun toggleIsEnableUser(user: User, isEnable: Boolean): Boolean?

    override fun loadUserByUsername(username: String): UserDetailsDTO

    fun verifyToken(string: String):String?


}