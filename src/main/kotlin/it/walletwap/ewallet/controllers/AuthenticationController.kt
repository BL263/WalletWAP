package it.walletwap.ewallet.controllers

import it.walletwap.ewallet.dto.CustomerDto
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.dto.WalletDTO
import it.walletwap.ewallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthenticationController {
    @Autowired
    lateinit var userService:UserDetailsService

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    fun register(@RequestBody @Valid user: UserDetailsDTO = UserDetailsDTO("","",true,"","","","","","")): Optional<UserDetailsDTO>? {
        return userService.registerUser(user)
    }
}