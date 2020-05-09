package com.ishroid.example.app.database

import com.ishroid.example.app.utils.arrayOfString
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object IssueTable : IntIdTable() {
    val userId: Column<UUID> = uuid("userId").references(UserTable.id)
    val title = varchar("title", 100)
    val description = varchar("description", 255)
    val refImages = arrayOfString("refImages")
    val refVideos = arrayOfString("refVideos")
}
