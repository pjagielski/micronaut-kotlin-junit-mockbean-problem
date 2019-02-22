package io.realworld.security.domain

import io.micronaut.security.utils.SecurityService
import io.micronaut.spring.tx.annotation.Transactional
import io.realworld.shared.domain.ApplicationException
import io.realworld.user.domain.LoggedUser
import io.realworld.user.domain.UserReadRepository
import javax.inject.Singleton

@Singleton
@Transactional
class LoggedUserService(
        private val securityService: SecurityService,
        private val userReadRepository: UserReadRepository
) {

    fun loggedUser(): LoggedUser? {
        return securityService.username().map { username -> userReadRepository.findByEmail(username) }.orElse(null)
    }

    fun loggedUserOrThrow() = loggedUser() ?: throw UserNotAuthorizedException()
}

class UserNotAuthorizedException : ApplicationException("User not authorized")
