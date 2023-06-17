package com.example.features.orders

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureOrdersRouting(){
    routing {
        route("/orders"){
            val controller = OrdersController()
            route("/get"){
                get {
                    controller.giveUserOrders(call)
                }
            }
            route("/all"){

            }
        }
    }
}