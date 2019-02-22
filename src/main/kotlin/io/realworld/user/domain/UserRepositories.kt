package io.realworld.user.domain

import io.realworld.shared.domain.ApplicationException
import io.realworld.shared.refs.UserId

interface UserReadRepository {
    fun findBy(userId: UserId): User?
    fun findBy(username: Username): User?
    fun findByEmail(email: String): User?
}
