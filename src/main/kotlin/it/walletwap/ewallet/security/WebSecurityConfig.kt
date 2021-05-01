package it.walletwap.ewallet.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class WebSecurityConfig: WebSecurityConfigurerAdapter(){
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var authenticationEntryPoint: AuthEntryPointJwt

    /*@Bean
    @Override
    fun authenticationManagerBean(): AuthenticationManager{
        return super.authenticationManagerBean()
    }*/

    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  //We will use it later
            .authorizeRequests().antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

    }

    override fun configure (auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

}