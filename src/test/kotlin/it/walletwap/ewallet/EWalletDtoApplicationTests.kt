package it.walletwap.ewallet

import it.walletwap.ewallet.domain.Rolename
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.RegisterForm
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.services.UserDetailsService
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
        val user1 = RegisterForm("pippo", "behnam2", "pass", "behnam263@gmail.com", "torino", "12345", "12345")
        userService.registerUser(user1)
        val registeredUser=userService.getUserByUserName(user1.username)
        assert( registeredUser?.username==user1.username && registeredUser.id!=null)
    }
    @Test
    fun userAddRemoveRoleTest() {
        val user1 = User(null, "behnam2", "pass", "behnam263@gmail.com", true, Rolename.CUSTOMER.name)
        user1.addRole(Rolename.ADMIN)
        assert(user1.roles.contains(Rolename.ADMIN.name) && user1.roles.contains(Rolename.CUSTOMER.name))

        user1.removeRole(Rolename.ADMIN)
        assert(!user1.roles.contains(Rolename.ADMIN.name).toString().contains(Rolename.ADMIN.name))

    }
}
