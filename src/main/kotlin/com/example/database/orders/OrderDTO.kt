package com.example.database.orders

import com.example.features.orders.OrderResponseRemote

data class OrderDTO(
    val id: Int,
    val car: Int,
    val user: Int,
    val description: String
){
    fun toOrderResponseRemote(): OrderResponseRemote {
        return OrderResponseRemote(
            id = id,
            car = car,
            description = description
        )
    }
}
