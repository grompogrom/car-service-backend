package com.example

import com.example.features.account.configureAccountRouting
import com.example.features.garage.configureGarageRouting
import com.example.features.login.configureLoginRouting
import com.example.features.orders.configureOrdersRouting
import com.example.features.records.configureRecordsRouting
import com.example.features.register.configureRegisterRouting
import com.example.features.services.configureServicesRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    val appDB = Database.connect(
        url = "jdbc:postgresql://192.168.31.33:5432/carapi",
        driver = "org.postgresql.Driver",
        user = "caruser",
        password = "181702")

    embeddedServer(Netty, port = 8080, host = "192.168.31.214", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureServicesRouting()
    configureAccountRouting()
    configureGarageRouting()
    configureOrdersRouting()
    configureRecordsRouting()
    configureSerialization()
}
