package com.example.features.account

import com.example.database.avatars.Avatars
import com.example.database.tokens.Tokens
import com.example.database.users.Users
import com.example.utils.TokenCheck
import com.example.utils.isValidFields
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class AccountController {
    suspend fun giveUserAccount(call: ApplicationCall){

        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!

        val userAccount = Users.fetchUser(memoryToken.userID)!!

        call.respond(
            userAccount.toAccountRemote()
        )
    }

    suspend fun updateUserAccount(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!
        val accountRemote = call.receive<AccountRemote>()

        if (!accountRemote.isValidFields()){
            call.respond(HttpStatusCode.BadRequest, "Fields are not valid")
            return
        }

        val userAccount = Users.fetchUser(memoryToken.userID)!!
        val newAccount = accountRemote.toUserDTO(
            userAccount.id,
            userAccount.password,
            userAccount.regDate)

        val isEmailAlreadyUsed = Users.fetchAllUsers().count { it.email == newAccount.email && it.id != newAccount.id} > 0
        if (isEmailAlreadyUsed){
            call.respond(HttpStatusCode.Conflict, "User with this email already exists")
            return
        }
        val result = Users.updateUser(userAccount.id, newAccount)

        call.respond(
            result.toAccountRemote()
        )
    }

    suspend fun updateUserPhoto(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!
        val receiveRemote = call.receive<UpdatePhotoReceiveRemote>()

        val userPhoto = Avatars.fetch(memoryToken.userID)
        userPhoto?.let {
            Avatars.update(receiveRemote.toAvatarDTO(memoryToken.userID))
        } ?: Avatars.insert(receiveRemote.toAvatarDTO(memoryToken.userID))
        call.respond(receiveRemote)
    }

    suspend fun giveUserPhoto(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!

        val avatar = Avatars.fetch(memoryToken.userID)
        avatar?.let {
            call.respond(UserPhotoResponseRemote(it.img))
        } ?: call.respond(HttpStatusCode.NoContent, "Avatar not set")
    }
}