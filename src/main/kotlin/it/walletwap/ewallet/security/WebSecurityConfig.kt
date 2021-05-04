package it.walletwap.ewallet.security

import it.walletwap.ewallet.services.impl.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
class WebSecurityConfig: WebSecurityConfigurerAdapter(){
    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    lateinit var authenticationEntryPoint: AuthEntryPointJwt

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationJwtTokenFilter(): JwtAuthenticationTokenFilter {
        return JwtAuthenticationTokenFilter()
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  //We will use it later
            .authorizeRequests().antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

    }

    override fun configure (auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

}