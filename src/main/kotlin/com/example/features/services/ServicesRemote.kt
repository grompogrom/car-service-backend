package com.example.features.services

import kotlinx.serialization.Serializable

@Serializable
data class ServicesResponseRemote(
    val services: List<ServiceResponseRemote>
)

@Serializable
data class ServiceResponseRemote(
    val id: Int,
    val name: String,
    val description: String
)