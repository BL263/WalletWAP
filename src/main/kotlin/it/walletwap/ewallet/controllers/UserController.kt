package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.RegisterForm
import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.services.CustomerService
import it.walletwap.ewallet.services.TransactionsService
import it.walletwap.ewallet.services.UserDetailsService
import it.walletwap.ewallet.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class UserController {
    @Autowired
    lateinit var userDetailsService: UserDetailsService


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun addWallet(@RequestBody @Valid registerForm: RegisterForm): String {
        if (registerForm.password == registerForm.confirmPassword){
            return userDetailsService.saveuser(registerForm).toString()
        } else return "Passwords do not match"

    }
}