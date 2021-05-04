package it.walletwap.ewallet.controllers


import it.walletwap.ewallet.JwtResponse
import it.walletwap.ewallet.dto.UserDetailsDto
import it.walletwap.ewallet.security.JwtUtils
import it.walletwap.ewallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/auth")
class UserController {
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtils: JwtUtils


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid userDetailsDto: UserDetailsDto): String {
        if (userDetailsDto.password == userDetailsDto.confirmPassword) {
            return userDetailsService.registerUser(userDetailsDto).toString()
        } else return "Passwords do not match"
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signInUser(@RequestParam(name = "username") username: String, @RequestParam(name = "password") password: String): ResponseEntity<Any>{
        //Perform authentication
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken(authentication)
        val userDetails: UserDetailsDto = authentication.principal as UserDetailsDto
        //Response with a JWT in order to give to the authenticated user a proof that he is authenticated (to perform request on protected path)
        return ResponseEntity.ok<Any>(
            JwtResponse(
                jwt,
                userDetails.username,
                userDetails.email,
            )
        )
    }
}