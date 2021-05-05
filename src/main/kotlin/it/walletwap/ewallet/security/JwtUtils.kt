package it.walletwap.ewallet.security

import io.jsonwebtoken.*
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtUtils(val userRepository: UserRepository) {

    @Value("\${application.jwt.jwtExpirationMs}")
    var jwtExpiration: Int = 60000

    @Value("\${application.jwt.jwtSecret}")
    lateinit var jwtSecret: String


    fun generateJwtToken (authentication: Authentication): String
        {

            val userPrincipal: UserDetailsDTO = authentication.principal as UserDetailsDTO
            return Jwts.builder()
                .setSubject((userPrincipal.username))
                .claim("roles", userPrincipal.roles)
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact()

        }

    fun validateJwtToken (authToken: String): Boolean
        {
            try{
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            } catch (e: SignatureException) {
                System.err.println("Invalid JWT signature: ${e.message}")
            } catch (e: MalformedJwtException) {
                System.err.println("Invalid JWT token: ${e.message}" )
            } catch (e: ExpiredJwtException) {
                System.err.println("JWT token is expired: ${e.message}" )
            } catch (e: UnsupportedJwtException) {
                System.err.println("JWT token is unsupported: ${e.message}" )
            } catch (e: IllegalArgumentException) {
                System.err.println("JWT claims string is empty: ${e.message}" )
            }
            return true
        }

    fun getDetailsFromJwtToken(authToken: String): UserDetailsDTO
    {
        val username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).body.subject
        return userRepository.findByUsername(username)!!.toDto()
    }
}