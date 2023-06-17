package com.example.database.services

import com.example.features.services.ServiceResponseRemote

class ServiceDTO (
    val id: Int,
    val name: String,
    val description: String
){
    fun toServicesResponseRemote() = ServiceResponseRemote(
        id = id,
        name = name,
        description = description
    )
}