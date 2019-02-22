package io.realworld.user.endpoint

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.token.jwt.generator.AccessRefreshTokenGenerator
import io.micronaut.security.token.jwt.render.AccessRefreshToken
import io.realworld.security.domain.UserAuthenticationService
import io.realworld.security.infrastructure.CustomUserDetails
import javax.validation.Valid

@Controller(UsersEndpoint.PATH)
class UsersEndpoint(
        private val userAuthenticationService: UserAuthenticationService,
        private val accessRefreshTokenGenerator: AccessRefreshTokenGenerator
) {

    companion object {
        const val PATH = "/users"
        const val LOGIN_PATH = "/login"
    }

    @Post(LOGIN_PATH)
    fun login(@Valid @Body loginRequest: LoginRequest) = UserResponse(
            loginRequest.user.run {
                val user = userAuthenticationService.authenticate(email, password)
                val token = accessRefreshTokenGenerator.generate(CustomUserDetails(user.id, user.email))
                user.toDto(token.map(AccessRefreshToken::getAccessToken).orElse(null))
            })
}
