package com.example.features.register

import com.example.cache.InMemoryCache
import com.example.cache.TokenCache
import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.UserDTO
import com.example.database.users.Users
import com.example.utils.isValidEmail
import com.example.utils.isValidFields
import com.example.utils.isValidPassword
import com.example.utils.isValidPhone
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class RegisterController {

    suspend fun registerNewUser(call: ApplicationCall){
        val receive = call.receive<RegisterReceiveRemote>()

        val user = Users.fetchUser(receive.email)
        user?.let {
            call.respond(HttpStatusCode.BadRequest, "Email already exists")
            return
        }

        when{
            !receive.email.isValidEmail() -> {
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
                return
            }
            !receive.password.isValidPassword() -> {
                call.respond(HttpStatusCode.BadRequest, "Password is not valid")
                return
            }
            !(receive.phone?.isValidPhone() ?: true) -> {
                call.respond(HttpStatusCode.BadRequest, "Phone is not valid")
                return
            }
        }

        if (!receive.isValidFields()){
            call.respond(HttpStatusCode.BadRequest, "User data is not valid")
            return
        }

        val token = UUID.randomUUID().toString()
        Users.insert(
            receive.toUserDTO(0)
        )
        val userID = Users.fetchUser(
            userEmail = receive.email
        )?.id

        Tokens.insert(
            TokenDTO(
                token = token,
                userID = userID!!
            )
        )

        call.respond(RegisterResponseRemote(token = token))
    }


}