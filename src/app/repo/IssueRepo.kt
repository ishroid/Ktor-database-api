package com.ishroid.example.app.repo

import com.ishroid.example.app.database.IssueTable
import com.ishroid.example.app.database.UserTable
import com.ishroid.example.app.model.Issue
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class IssueRepo {
    suspend fun create(issue: Issue) {
        transaction {
            IssueTable.insert {
                it[userId] = issue.userUuid
                it[title] = issue.title
                it[description] = issue.description
                it[refImages] = issue.refImages
                it[refVideos] = issue.refVideos
            }
        }
    }

    suspend fun getAll(): List<Issue> {
        return transaction {
            IssueTable.selectAll().map {
                it.toIssue()
            }
        }
    }

    suspend fun get(id: Int): Issue? {
        return transaction {
            IssueTable.select { IssueTable.id eq id }.map {
                it.toIssue()
            }.firstOrNull()
        }
    }

    private fun ResultRow.toIssue(): Issue {
        return Issue(this[IssueTable.userId], this[IssueTable.id].value, this[IssueTable.title], this[IssueTable.description], this[IssueTable.refImages], this[IssueTable.refVideos])
    }
}