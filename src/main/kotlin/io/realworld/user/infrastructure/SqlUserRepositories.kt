package io.realworld.user.infrastructure

import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.infrastructure.stringWrapper
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.domain.Username
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import javax.inject.Singleton

object UserTable : Table("users") {
    val id = userId("id").primaryKey().autoIncrement()
    val username = username("username")
    val email = text("email")
    val password = text("password")
    val bio = text("bio").nullable()
    val image = text("image").nullable()
}

@Singleton
class SqlUserReadRepository : UserReadRepository {

    override fun findBy(userId: UserId) =
            UserTable.selectSingleOrNull { UserTable.id eq userId }?.toUser()

    override fun findBy(username: Username) =
            UserTable.selectSingleOrNull { UserTable.username eq username }?.toUser()

    override fun findByEmail(email: String) =
            UserTable.selectSingleOrNull { UserTable.email eq email }?.toUser()
}

fun ResultRow.toUser(userIdColumn: Column<UserId> = UserTable.id) = User(
        id = this[userIdColumn],
        username = this[UserTable.username],
        email = this[UserTable.email],
        password = this[UserTable.password],
        bio = this[UserTable.bio],
        image = this[UserTable.image]
)

fun UpdateBuilder<Any>.from(user: User) {
    this[UserTable.username] = user.username
    this[UserTable.email] = user.email
    this[UserTable.password] = user.password
    this[UserTable.bio] = user.bio
    this[UserTable.image] = user.image
}

fun Table.userId(name: String) = longWrapper<UserId>(name, UserId::Persisted, UserId::value)

fun Table.username(name: String) = stringWrapper(name, ::Username, Username::value)
