package it.walletwap.ewallet

import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.services.UserDetailsService
import it.walletwap.ewallet.services.impl.UserDetailsServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EWalletDTOApplicationTests {
    @Autowired
    lateinit var userRepo:UserRepository
    @Autowired
    lateinit var customerRepo:CustomerRepository

    @Autowired
    lateinit var userService: UserDetailsService

    @Test
    fun userRegisterTest() {
        val user1 = User(null, "behnam2", "pass", "behnam263@gmail.com", true, Rolename.CUSTOMER.name)
        userService.registerUser(user1.toDto())
        val registeredUser=userService.getuserByUserName(user1.username)
        assert( registeredUser?.username==user1.username && registeredUser.id!=null)
    }
    @Test
    fun userAddRemoveRoleTest() {
        val user1 = User(null, "behnam2", "pass", "behnam263@gmail.com", true, Rolename.CUSTOMER.name)
        userService.addRoleName(user1, Rolename.ADMIN.name)
        assert(user1.roles?.contains(Rolename.ADMIN.name)!! && user1.roles?.contains(Rolename.CUSTOMER.name)!!)

        userService.removeRoleName(user1, Rolename.ADMIN.name)
        assert(!user1.roles?.contains(Rolename.ADMIN.name).toString().contains(Rolename.ADMIN.name))

    }
}
