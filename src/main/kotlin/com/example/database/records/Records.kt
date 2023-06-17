package com.example.database.records

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object Records: Table("records") {
    val id = Records.integer("id").autoIncrement()
    val user = Records.integer("user")
    val car = Records.integer("car")
    val datetime = Records.datetime("datetime")
    val description = Records.varchar("description", 200).nullable()

    fun insert(recordDTO: RecordDTO){
        transaction {
            insert {
                it[user] = recordDTO.user
                it[car] = recordDTO.car
                it[datetime] = recordDTO.datetime
            }
        }
    }

    fun delete(recordID: Int){
        transaction {
            Records.deleteWhere { Records.id.eq(recordID) }
        }
    }

    fun fetchAll(): List<RecordDTO>{
        return transaction {
            val records = Records.selectAll()
            records.map {
                RecordDTO(
                    id = it[Records.id],
                    user = it[Records.user],
                    car = it[Records.car],
                    datetime = it[Records.datetime]
                )
            }
        }
    }

    fun fetchAllRelevant():List<RecordDTO>{
        return transaction {
            val records = Records.select{ datetime.less(LocalDateTime.now())}
            records.map {
                RecordDTO(
                    id = it[Records.id],
                    user = it[Records.user],
                    car = it[Records.car],
                    datetime = it[Records.datetime]
                )
            }
        }
    }

    fun fetch(userID: Int):List<RecordDTO>{
        return transaction {
            val records = Records.select { user.eq(userID) }
            records.map{
                RecordDTO(
                    id = it[Records.id],
                    user = it[Records.user],
                    car = it[Records.car],
                    datetime = it[Records.datetime]
                )
            }
        }
    }
    fun fetchRelevant(userID: Int):List<RecordDTO>{
        return transaction {
            val records = Records.select { user.eq(userID) and datetime.greater(LocalDateTime.now()) }
            records.map{
                RecordDTO(
                    id = it[Records.id],
                    user = it[Records.user],
                    car = it[Records.car],
                    datetime = it[Records.datetime]
                )
            }
        }
    }
}