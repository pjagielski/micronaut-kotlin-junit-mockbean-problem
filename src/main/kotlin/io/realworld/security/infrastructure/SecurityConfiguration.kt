package io.realworld.security.infrastructure

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.security.authentication.providers.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Factory
class SecurityConfiguration {

    val encoder = BCryptPasswordEncoder()

    @Bean
    fun passwordEncoder() = object : PasswordEncoder {
        override fun encode(rawPassword: String) = encoder.encode(rawPassword)

        override fun matches(rawPassword: String, encodedPassword: String) = encoder.matches(rawPassword, encodedPassword)
    }
}
