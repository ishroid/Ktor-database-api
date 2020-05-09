package com.ishroid.example.app.repo

import com.ishroid.example.app.database.UserTable
import com.ishroid.example.app.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepo {

    suspend fun create(user: User) {
        transaction {
            UserTable.insert {
                it[name] = user.name
                it[email] = user.email
                it[city] = user.city
            }
        }
    }

    suspend fun get(id: String): User? {
        return transaction {
            UserTable.select { UserTable.id eq UUID.fromString(id) }.map {
                it.toUser()
            }.firstOrNull()
        }
    }

    suspend fun getAll(): List<User> {
        return transaction {
            UserTable.selectAll().map { it.toUser() }
        }
    }

    private fun ResultRow.toUser(): User {
        return User(this[UserTable.id].toString(), this[UserTable.name], this[UserTable.email], this[UserTable.city])
    }
}