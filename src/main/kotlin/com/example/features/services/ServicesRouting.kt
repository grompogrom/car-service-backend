package com.example.features.services

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureServicesRouting(){
    routing {
        get("/services"){
            val controller =  ServicesController()
            controller.giveServices(call)
        }
    }
}