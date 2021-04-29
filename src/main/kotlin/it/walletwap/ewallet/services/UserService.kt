package it.walletwap.ewallet.services


import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.UserDto
import java.util.*


interface UserService {

    /*
	 * Queries the database and returns the userDto corresponding to the userId passed as parameter.
	 * Throws an exception in case of invalid (negative) userId
	 * Returns null if no user is found with the given Id
	 */

    fun getuserById(userId: Long): Optional<UserDto>?

    /*
     * Stores the user passed as parameter in the application database
     */
    fun saveuser(userDto: UserDto?): Boolean

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

    fun getRoleName(user: User):String?

    fun addRoleName(user: User,roleTobeAdded:String):Boolean?

    fun removeRoleName(user: User,roleTobeRemoved:String):Boolean?
}