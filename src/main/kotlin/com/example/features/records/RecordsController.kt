package com.example.features.records

import com.example.database.garage.Cars
import com.example.database.records.Records
import com.example.database.tokens.Tokens
import com.example.utils.TokenCheck
import com.example.utils.isValidFields
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class RecordsController {
    suspend fun giveUserRecords(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!

        val records = Records.fetchRelevant(memoryToken.userID)
        call.respond(RecordListGetResponseRemote(records.map { it.toRecordsGetResponseRemote() }))
    }

    suspend fun deleteUserRecord(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!
        val userRecords = Records.fetch(userID = memoryToken.userID)
        val receiveRemote = call.receive<RecordsDeleteReceiveRemote>()
        if (receiveRemote.id !in userRecords.map { it.id }){
            call.respond(HttpStatusCode.BadRequest, "Record does not exists")
            return
        }
        Records.delete(receiveRemote.id)
        call.respond(HttpStatusCode.OK)
    }

    suspend fun addUserRecord(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token does not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!
        val receiveRemote = call.receive<RecordsAddReceiveRemote>()

        if (!receiveRemote.isValidFields()){
            call.respond(HttpStatusCode.BadRequest, "Fields are not valid")
            return
        }
        val cars = Cars.fetchGarage(memoryToken.userID)
        if (cars.isEmpty() || receiveRemote.car !in cars.map { it.id }){
            call.respond(HttpStatusCode.BadRequest, "Car does not exits ")
            return
        }
        Records.insert(
            receiveRemote.toRecordsDTO(0, memoryToken.userID)
        )
        call.respond(HttpStatusCode.OK)

    }
}