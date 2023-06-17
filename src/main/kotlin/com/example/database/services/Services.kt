package com.example.database.services

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Services: Table("services") {
    private val id = Services.integer("id").autoIncrement()
    private val name = Services.varchar("name", 30)
    private val description = Services.varchar("description", 200)
    override val primaryKey = PrimaryKey(id, name = "PK_Services_id")

    fun insert(serviceDTO: ServiceDTO){
        transaction {
            Services.insert {
                it[id] = serviceDTO.id
                it[name] = serviceDTO.name
                it[description] = serviceDTO.description
            }
        }
    }

    fun fetchServices(): List<ServiceDTO> {
        return  transaction {
            val services = Services.selectAll()

              services.toList().map {
                ServiceDTO(
                    id = it[Services.id],
                    name = it[name],
                    description = it[description]
                )
            }
        }

    }
}