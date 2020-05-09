package com.ishroid.example.app.routing

import com.ishroid.example.app.model.Issue
import com.ishroid.example.app.repo.IssueRepo
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.get
import io.ktor.routing.post

fun Route.issueRout(issueRepo: IssueRepo) {

    route("/issue") {
        get("/{issueId}") {
            val issueId = call.parameters["issueId"]
                    ?: throw IllegalArgumentException("Parameter issue Id not found")
            issueRepo.get(issueId.toInt())?.let { issue -> call.respond(issue) }
        }

        get("/") {
            call.respond(issueRepo.getAll())
        }

        post("/create") {
            val receivedIssue = call.receive(Issue::class)
            call.respond(issueRepo.create(receivedIssue))
        }
    }
}