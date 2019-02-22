package io.realworld.security.infrastructure

import com.nimbusds.jwt.JWTClaimsSet
import io.micronaut.context.annotation.Replaces
import io.micronaut.runtime.ApplicationConfiguration
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.micronaut.security.token.config.TokenConfiguration
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator
import io.reactivex.Flowable
import io.realworld.security.domain.UserAuthenticationService
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import org.reactivestreams.Publisher
import javax.annotation.Nullable
import javax.inject.Singleton

class CustomUserDetails(
        val userId: UserId,
        email: String
) : UserDetails(email, listOf())

@Singleton
class CustomAuthenticationProvider(val userAuthenticationService: UserAuthenticationService) : AuthenticationProvider {

    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        val email = authenticationRequest.identity as String
        val password = authenticationRequest.secret as String
        val user = userAuthenticationService.authenticate(email, password)
        return Flowable.just(user.toUserDetails())
    }
}

private fun User.toUserDetails() = CustomUserDetails(this.id, this.email)

@Singleton
@Replaces(bean = JWTClaimsSetGenerator::class)
class CustomJWTClaimsSetGenerator(tokenConfiguration: TokenConfiguration,
                                  @Nullable jwtIdGenerator: JwtIdGenerator?,
                                  @Nullable claimsAudienceProvider: ClaimsAudienceProvider?,
                                  @Nullable applicationConfiguration: ApplicationConfiguration?
) : JWTClaimsSetGenerator(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration) {

    override fun populateWithUserDetails(builder: JWTClaimsSet.Builder, userDetails: UserDetails) {
        super.populateWithUserDetails(builder, userDetails)
        if (userDetails is CustomUserDetails) {
            builder.issuer(userDetails.userId.value.toString())
        }
    }
}
