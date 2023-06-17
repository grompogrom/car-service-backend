package com.example.database.orders

import com.example.database.garage.Cars
import com.example.database.users.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Orders: Table("orders") {
    val id = Orders.integer("id").autoIncrement()
    val car = Orders.integer("car").references(Cars.id)
    val user = Orders.integer("user").references(Users.id)
    val description = Orders.varchar("description", 200)
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id, name = "PK_orders_id")

    fun insert(orderDTO: OrderDTO){
        transaction {
            Orders.insert {
                it[car] = orderDTO.car
                it[user] = orderDTO.user
                it[description] = orderDTO.description
            }
        }
    }

    fun fetchAll(): List<OrderDTO>{
        return transaction {
            val orders = Orders.selectAll()
            orders.map {
                OrderDTO(
                    id = it[Orders.id],
                    car = it[car],
                    user = it[user],
                    description = it[description]
                )
            }
        }
    }

    fun fetch(userID: Int): List<OrderDTO>{
        return transaction {
            val orders = Orders.select { user.eq(userID) }
            orders.map {
                OrderDTO(
                    id = it[Orders.id],
                    car = it[car],
                    user = it[user],
                    description = it[description]
                )
            }
        }
    }
}