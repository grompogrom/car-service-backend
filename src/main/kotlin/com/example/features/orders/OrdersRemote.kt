package com.example.features.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseRemote(
    val id: Int,
    val car: Int,
    val description: String
)

@Serializable
data class OrdersLIstResponseRemote(
    val orders: List<OrderResponseRemote>
)
