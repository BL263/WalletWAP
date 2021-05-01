package it.walletwap.ewallet.services


import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.UserDetailsDTO
import java.util.*


interface UserDetailsService {

    /*
	 * Queries the database and returns the userDto corresponding to the userId passed as parameter.
	 * Throws an exception in case of invalid (negative) userId
	 * Returns null if no user is found with the given Id
	 */

    fun getuserByUserName(username: String): User?

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
    fun deleteuser(userId: Int?): Boolean?

    fun enableUser(user: User, isEnable: Boolean): Boolean?

    fun loadUserByUsername(username: String): UserDetailsDTO

}