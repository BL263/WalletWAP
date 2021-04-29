package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsServiceImpl : UserDetailsService,Extensions() {
	@Autowired
	lateinit var repositoryUser: UserRepository
	override fun getuserById(userId: Long): Optional<UserDetailsDTO>? {
		TODO("Not yet implemented")
	}

	override fun saveuser(userDetailsDTO: UserDetailsDTO?): Boolean {
		TODO("Not yet implemented")
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
	val user=	repositoryUser.findByUsername(username)
		if(user==null) throw UserException("user not found")
		else
			return user.toDto()
	}

	override fun getRoleName(user: User):String? {
		return user.roles
	}

	override fun addRoleName(user: User,roleTobeAdded:String):Boolean? {
		if(user.roles.isNullOrEmpty())
			user.roles=roleTobeAdded
		else
			user.roles+= ",$roleTobeAdded"
		return  true
	}

	override fun removeRoleName(user: User,roleTobeRemoved:String):Boolean? {
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

	}


}

class UserException(message:String): Exception(message)