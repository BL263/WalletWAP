package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.Rolename
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.RegisterForm
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.repositories.UserRepository
import it.walletwap.ewallet.services.MailService
import it.walletwap.ewallet.services.NotificationService
import it.walletwap.ewallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsServiceImpl(var userRepository: UserRepository, var customerRepository: CustomerRepository): UserDetailsService {
    @Autowired
	lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var mailService: MailService

    @Autowired
    lateinit var notificationService: NotificationService

    var domainAddress = "http://localhost:8080"

    override fun getUserByUserName(username: String): User? {
       return userRepository.findByUsername(username)
    }

    override fun enableUser(username: String): Boolean? {
        val user=getUserByUserName(username)
        return if(user!=null)toggleIsEnableUser(user,true)
        else false
    }
    override fun disableUser(username: String): Boolean? {
        val user=getUserByUserName(username)
        return if(user!=null)toggleIsEnableUser(user,false)
        else false
    }

    override fun toggleIsEnableUser(user: User, isEnable: Boolean): Boolean? {
        user.isEnabled = isEnable
        return user.isEnabled == isEnable
    }

    override fun loadUserByUsername(username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username)
        if (user != null) return user.toDto()
        else throw UserException("user not found")

    }


    override fun registerUser(registerForm: RegisterForm): UserDetailsDTO? {
        if(registerForm.username.isEmpty()) return null
        if(registerForm.email.isEmpty()) return null
        // if user exists new user can not register
        if(userRepository.findByUsername(registerForm.username)!=null) return null
        if(registerForm.password!=registerForm.confirmPassword) return null

        val user = User(username = registerForm.username, email =registerForm.email ,isEnabled = false, password = encoder.encode(registerForm.password), roles = Rolename.CUSTOMER.toString())
        val customer = Customer(name = registerForm.name, surname = registerForm.surname, deliveryAddress = registerForm.address, email = registerForm.email, user = user)
        userRepository.save(user)
        customerRepository.save(customer)

        val tokenizedUrl =
            "${domainAddress}/auth/registrationConfirm?token=${notificationService.createToken(user.username)}"

        mailService.sendMessage(
            registerForm.email, "Registration successful",
            "Dear ${registerForm.name} ${registerForm.surname} " +
                    "Thank you for the registration." +
                    "Please browse the link $tokenizedUrl in your browser"
        )
        return user.toDto()
    }

    override fun verifyToken(token: String): String? {
        val tokenObj = notificationService.checkToken(token)
        return if (tokenObj?.username.isNullOrEmpty()) {
            "Wrong Token"
        } else {
            val userFound = getUserByUserName(tokenObj?.username.toString())
            if (userFound != null && tokenObj?.expiryDate?.compareTo(Date())!!>0) {
                toggleIsEnableUser(userFound, isEnable = true)
                "Successfully registered"
            } else
                "Wrong Token"

        }
    }
}


class UserException : Exception {
	var errorCode: Int = 300
		private set
	constructor(message: String?) : super(message) {}
	constructor(message: String?, cause: Throwable?) : super(message, cause) {}
	constructor(message: String?, cause: Throwable?, errorCode: Int) : super(message, cause) {}

}