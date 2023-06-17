package com.example.features.records

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRecordsRouting(){
    routing {
        route("/records"){
            val controller = RecordsController()
            route("/create"){
                post {
                    controller.addUserRecord(call)
                }
            }
            route("/get"){
                get {
                    controller.giveUserRecords(call)
                }
            }
            route("/delete"){
                post {
                    controller.deleteUserRecord(call)
                }
            }

        }
    }
}