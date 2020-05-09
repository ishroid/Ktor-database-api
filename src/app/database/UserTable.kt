package com.ishroid.example.app.database

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {
    val name = varchar("name", 100)
    val email = varchar("email", 100)
    val city = varchar("city", 20)
}