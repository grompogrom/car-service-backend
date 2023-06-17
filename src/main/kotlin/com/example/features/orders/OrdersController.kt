package com.example.features.orders

import com.example.database.orders.Orders
import com.example.database.tokens.Tokens
import com.example.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class OrdersController {
    suspend fun giveUserOrders(call: ApplicationCall){
        val token = call.request.headers["token"] ?: ""
        if (!TokenCheck.isTokenValid(token)){
            call.respond(HttpStatusCode.Unauthorized, "Token not exist")
            return
        }
        val memoryToken = Tokens.fetchToken(token)!!

        val userOrders = Orders.fetch(memoryToken.userID)
        call.respond(
            OrdersLIstResponseRemote(userOrders.map { it.toOrderResponseRemote() })
        )
    }
}