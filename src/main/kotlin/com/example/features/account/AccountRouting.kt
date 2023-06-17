package com.example.features.account

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAccountRouting(){
    routing{
        route("/account"){
            val controller = AccountController()
            route("/load"){
                get {
                    controller.giveUserAccount(call)
                }
            }
            route("/update"){
                post {
                    controller.updateUserAccount(call)
                }

            }
            route("/photo"){
                route("/load"){
                    get {
                        controller.giveUserPhoto(call)
                    }
                }
                route("/update"){
                    post {
                        controller.updateUserPhoto(call)
                    }
                }
                }
            }
        }
    }
