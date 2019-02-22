package io.realworld.user.endpoint

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.domain.Username
import io.realworld.user.infrastructure.SqlUserReadRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
open class UsersEndpointTest {

    val embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
    val client: RxHttpClient = RxHttpClient.create(embeddedServer.url)

    @Inject
    lateinit var userReadRepository: UserReadRepository

    @MockBean(SqlUserReadRepository::class)
    fun userReadRepository(): UserReadRepository = mock()

    @Test
    fun `should login on correct credentials`() {
        val user = User(id = UserId.Persisted(100), username = Username("test"), password = "test123", email = "tomek@przytomek.pl", bio = null, image = null)
        whenever(userReadRepository.findByEmail(user.email)).thenReturn(user)

        val request = POST(
                "${UsersEndpoint.PATH}${UsersEndpoint.LOGIN_PATH}",
                LoginRequest(user = LoginDto(user.email, user.password))
        )
        val response = client.toBlocking().exchange(request, UserResponse::class.java)

        assertThat(response.status).isEqualTo(HttpStatus.OK)
    }
}
