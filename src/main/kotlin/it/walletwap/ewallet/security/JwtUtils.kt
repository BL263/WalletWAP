package it.walletwap.ewallet.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import it.walletwap.ewallet.domain.EmailVerificationToken
import it.walletwap.ewallet.domain.User
import it.walletwap.ewallet.dto.UserDetailsDTO
import it.walletwap.ewallet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher.SECRET_KEY
import javax.servlet.http.HttpServletRequest
import javax.xml.bind.DatatypeConverter


@Component
class JwtUtils {

    @Autowired
    lateinit var httpServletRequest: HttpServletRequest

    @Value("\${application.jwt.jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${application.jwt.jwtExpirationMs}")
    lateinit var expirationTime: String

    @Autowired
    lateinit var userRepository: UserRepository

    fun generateJwtToken(authentication: Authentication): String {
        return Jwts.builder()
            .setIssuer(authentication.principal.toString())
            .setExpiration(Date(System.currentTimeMillis() + expirationTime.toLong()))
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        return try {
            val parsedToken = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parse(authToken)
            println(parsedToken)
            true
        } catch (e: io.jsonwebtoken.MalformedJwtException) {
            httpServletRequest.setAttribute("Malformed JWT", e.message)
            false
        } catch (e: io.jsonwebtoken.security.SignatureException) {
            httpServletRequest.setAttribute("Invalid JWT signature", e.message)
            false
        } catch (e: io.jsonwebtoken.ExpiredJwtException) {
            httpServletRequest.setAttribute("Invalid JWT signature", e.message)
            false
        } catch (e: io.jsonwebtoken.ClaimJwtException) {
            httpServletRequest.setAttribute("Invalid JWT Claim", e.message)
            false
        } catch (e: io.jsonwebtoken.IncorrectClaimException) {
            httpServletRequest.setAttribute("Incorrect Claim", e.message)
            false
        } catch (e: io.jsonwebtoken.InvalidClaimException) {
            httpServletRequest.setAttribute("Invalid Claim", e.message)
            false
        } catch (e: io.jsonwebtoken.JwtException) {
            httpServletRequest.setAttribute("Invalid JWT", e.message)
            false
        } catch (e: io.jsonwebtoken.MissingClaimException) {
            httpServletRequest.setAttribute("Missing Claim", e.message)
            false
        }

    }

    fun getDetailsFromJwtToken(authToken: String): UserDetailsDTO? {
        return extractUser(Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken))?.toDto()
    }

    fun extractUser(parseClaimsJws: Jws<Claims>?): User? {
        return userRepository.findByUsername(parseClaimsJws?.body!!["iss"].toString())
    }

}