package com.example.features.login

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController {

    suspend fun loginUser(call: ApplicationCall){
        val receive = call.receive<LoginReceiveRemote>()
        val user = Users.fetchUser(receive.login)
        user?.let {
            if (receive.password == user.password){
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        token = token,
                        userID = user.id!!
                    )
                )
                call.respond(LoginResponseRemote(token))
            } else
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
        } ?:
            call.respond(HttpStatusCode.BadRequest, "User not found")

    }
}