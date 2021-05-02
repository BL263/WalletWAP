package it.walletwap.ewallet.controllers


import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class UserController {
    @Autowired
    lateinit var userDetailsService: UserDetailsService


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody @Valid registerForm: UserDetailsDTO): String {
        if (registerForm.password == registerForm.confirmPassword) {
            return userDetailsService.registerUser(registerForm).toString()
        } else return "Passwords do not match"

    }

    @GetMapping("/registrationConfirm")
    @ResponseStatus(HttpStatus.OK)
    fun checkToken(@RequestParam(
        name = "token",
        required = true,
        defaultValue = ""
    ) token: String):String{
        return userDetailsService.verifyToken(token).toString()
    }

}