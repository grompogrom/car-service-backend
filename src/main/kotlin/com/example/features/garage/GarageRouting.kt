package com.example.features.garage

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGarageRouting(){
    routing {
        route("/garage"){
            val controller = GarageController()
            route("/all"){
                get {
                    controller.giveGarage(call)
                }
            }
            route("/add"){
                post {
                    controller.addCar(call)
                }
            }
            route("/delete"){
                post {
                    controller.deleteCar(call)
                }
            }
        }
    }
}