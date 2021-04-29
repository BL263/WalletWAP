package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Transaction
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.TransactionDto
import it.walletwap.ewallet.dto.UserDto
import it.walletwap.ewallet.repositories.TransactionRepository
import it.walletwap.ewallet.repositories.WalletRepository
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService,Extensions() {
	@Autowired
	lateinit var repositoryWallet: WalletRepository
	override fun getuserById(userId: Long): Optional<UserDto>? {
		TODO("Not yet implemented")
	}

	override fun saveuser(userDto: UserDto?): Boolean {
		TODO("Not yet implemented")
	}

	override val allusers: List<Any?>?
		get() = TODO("Not yet implemented")

	override fun deleteuser(userId: Int?): Boolean? {
		TODO("Not yet implemented")
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