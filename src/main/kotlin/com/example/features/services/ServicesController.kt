package com.example.features.services

import com.example.database.services.Services
import io.ktor.server.application.*
import io.ktor.server.response.*

class ServicesController {
    suspend fun giveServices(call: ApplicationCall){
        val services = Services.fetchServices()
        call.respond(ServicesResponseRemote(services = services.map{it.toServicesResponseRemote()}))
    }
}