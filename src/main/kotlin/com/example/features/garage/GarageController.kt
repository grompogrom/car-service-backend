package com.example.features.garage

import com.example.database.garage.Cars
import com.example.database.tokens.Tokens
import com.example.utils.TokenCheck
import com.example.utils.isValidFields
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class GarageController {
    suspend fun giveGarage(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!

        val userID = memoryToken.userID
        val cars = Cars.fetchGarage(userID).map { it.toCarResponseRemote() }
        call.respond(GarageResponseRemote(cars))
    }

    suspend fun addCar(call: ApplicationCall){
        val carReceiveRemote = call.receive<AddCarReceiveRemote>()
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!

        if (!carReceiveRemote.isValidFields()){
            call.respond(HttpStatusCode.BadRequest, "Fields are not valid")
            return
        }

        val userID = memoryToken.userID
        val newCar = carReceiveRemote.toCarDTO(userID)
        val isVinNotUniq = Cars.fetchGarage(userID).count {  it.vin == newCar.vin } > 0
        if (isVinNotUniq){
            call.respond(HttpStatusCode.Conflict)
            return
        }
        Cars.insert(newCar)
        call.respond(HttpStatusCode.OK)

    }

    suspend fun deleteCar(call: ApplicationCall){
        val carID = call.receive<DeleteCarReceiveRemote>()
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }

        val memoryToken = Tokens.fetchToken(token)!!

        val userID = memoryToken.userID
        if (carID.id !in Cars.fetchGarage(userID).map { it.id }){
            call.respond(HttpStatusCode.BadRequest, "This car id not exists")
            return
        }

        Cars.delete(carID.id)
        call.respond(HttpStatusCode.OK)
    }
}