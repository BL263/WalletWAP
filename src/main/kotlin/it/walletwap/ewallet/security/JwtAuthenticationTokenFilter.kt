package it.walletwap.ewallet.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter: OncePerRequestFilter() {
    @Autowired
    lateinit var jwtUtils: JwtUtils

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            var jwt: String? = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                var userDetails: UserDetails = jwtUtils.getDetailsFromJwtToken(jwt);
                var authentication: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                );
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            System.err.println("Cannot set user authentication: ${e}")
        }
        filterChain.doFilter(request, response)
    }
    fun parseJwt(request: HttpServletRequest ): String? {
        try{
            var headerAuth: String = request.getHeader("Authorization")
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7, headerAuth.length);
            }
        } catch (e: NullPointerException){
            // None authorization header
        }

        return null;
    }
}