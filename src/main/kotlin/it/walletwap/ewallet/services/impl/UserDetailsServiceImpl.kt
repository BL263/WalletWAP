package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.Rolename
import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsServiceImpl(val userRepository: UserRepository,val customerRepository:CustomerRepository) : UserDetailsService,Extensions() {
	@Autowired
	lateinit var encoder: PasswordEncoder

    override fun getuserByUserName(username: String): User? {
       return userRepository.findByUsername(username)
    }

    override val allusers: List<Any?>?
        get() = TODO("Not yet implemented")

	override fun deleteuser(userId: Int?): Boolean? {
		TODO("Not yet implemented")
	}

	override fun enableUser(user: User,isEnable:Boolean): Boolean? {
		user.isEnabled=isEnable
		return user.isEnabled==isEnable
	}

    override fun loadUserByUsername(username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
        if (user == null) throw UserException("user not found")
        else
            return user.toDto()
    }

	// Moved them into User class as specification says
	/*fun getRoleName(user: User):String? {
		return user.roles
	}

	fun addRoleName(user: User,roleTobeAdded:String):Boolean? {
		if(user.roles.isNullOrEmpty())
			user.roles=roleTobeAdded
		else
			user.roles+= ",$roleTobeAdded"
		return  true
	}

	fun removeRoleName(user: User,roleTobeRemoved:String):Boolean? {
		if(user.roles?.contains(",") == true){
		user.roles=user.roles?.replace(",$roleTobeRemoved", "")
		return true}
		else
		{
			if(user.roles.isNullOrEmpty())
				return null
				else {
					true
				user.roles = user.roles?.replace(roleTobeRemoved, "")
			}
		}
		return  false
	}*/

	override fun registerUser(userdto: UserDetailsDTO): UserDetailsDTO? {
		if(userdto.username.isEmpty()) return null
		if(userdto.email.isEmpty()) return null
		// if user exists new user can not register
		if(userRepository.findByUsername(userdto.username)!=null) return null
		if(userdto.password!=userdto.confirmPassword) return null


		val user = User(username =userdto.userName, email =userdto.email ,isEnabled = false, password = encoder.encode(userdto.pass), roles = Rolename.CUSTOMER.toString())
		val customer = Customer(name = userdto.name, surname = userdto.surname, deliveryAddress = userdto.address, email = userdto.email, user = user)
		userRepository.save(user)
		customerRepository.save(customer)

		val mailService = MailServiceImpl()
		mailService.sendMessage(userdto.email,"Registration successful","Dear ${userdto.name} ${userdto.surname} Thank you for the registration.")

		return user.toDto()
	}

}


class UserException : Exception {
	var errorCode: Int = 300
		private set
	constructor(message: String?) : super(message) {}
	constructor(message: String?, cause: Throwable?) : super(message, cause) {}
	constructor(message: String?, cause: Throwable?, errorCode: Int) : super(message, cause) {

	}

}