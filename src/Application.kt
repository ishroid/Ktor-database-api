package com.ishroid.example

import com.ishroid.example.app.database.DatabaseFactory
import com.ishroid.example.app.repo.IssueRepo
import com.ishroid.example.app.repo.UserRepo
import com.ishroid.example.app.routing.issueRout
import com.ishroid.example.app.routing.userRout
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.Routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(DefaultHeaders)
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respondText(cause.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    install(ContentNegotiation) {
        gson {

        }
    }

    DatabaseFactory.init()
    install(Routing) {
        userRout(UserRepo())
        issueRout(IssueRepo())
    }
}

