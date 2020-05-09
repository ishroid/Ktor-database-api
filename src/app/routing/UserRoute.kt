package com.ishroid.example.app.routing

import com.ishroid.example.app.model.User
import com.ishroid.example.app.repo.UserRepo
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.userRout(userRepo: UserRepo) {
    route("/user") {

        get("/") {
            call.respond(userRepo.getAll())
        }

        get("/{userId}") {
            val userId = call.parameters["userId"]
                    ?: throw IllegalArgumentException("Parameter user id not found")

            userRepo.get(userId)?.let { user -> call.respond(user) } ?: kotlin.run {
                call.respond("User not found")
            }
        }

        post("/create") {
            val receivedUser = call.receive(User::class)
            call.respond(userRepo.create(receivedUser))
        }
    }
}