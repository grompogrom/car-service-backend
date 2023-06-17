package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val text: String,
    val tesis: String
)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(Test("test", "tesis"))
        }
    }
}
