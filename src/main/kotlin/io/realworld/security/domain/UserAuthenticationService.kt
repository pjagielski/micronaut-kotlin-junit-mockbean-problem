package io.realworld.security.domain

import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.providers.PasswordEncoder
import io.micronaut.spring.tx.annotation.Transactional
import io.realworld.user.domain.User
import io.realworld.user.domain.UserReadRepository
import javax.inject.Singleton

@Singleton
@Transactional
class UserAuthenticationService(private val userReadRepository: UserReadRepository,
                                private val passwordEncoder: PasswordEncoder) {

    fun authenticate(email: String, password: String): User {
        return userReadRepository.findByEmail(email)?.let { user ->
            if (passwordEncoder.matches(password, user.password)) user else null
        } ?: throw AuthenticationException("Bad credentials")
    }
}
