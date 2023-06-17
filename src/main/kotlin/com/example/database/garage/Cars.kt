package com.example.database.garage

import com.example.database.users.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Cars: Table("cars") {
    val id = Cars.integer("id").autoIncrement()
    private val owner = Cars.integer("owner").references(Users.id)
    private val mark = Cars.varchar("mark", 20)
    private val model = Cars.varchar("model", 20)
    private val engineDisplacement = Cars.double("eng_displacement")
    private val wheelDrive = Cars.varchar("wheel_drive", 10)
    private val year = Cars.date("year")
    private val vinType = Cars.varchar("vin_type", 10)
    private val vin = Cars.varchar("vin", 17)
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id, name = "PK_cars_id")

    fun fetchGarage(userID: Int):List<CarDTO>{
        return transaction {
            val cars = select { owner eq userID }
            cars.map {
                CarDTO(
                    id = it[Cars.id],
                    owner = it[owner],
                    mark = it[mark],
                    model = it[model],
                    engineDisplacement = it[engineDisplacement],
                    wheelDrive = it[wheelDrive],
                    year = it[year],
                    vinType = it[vinType],
                    vin = it[vin]
                )
            }
        }
    }

    fun insert(carDTO: CarDTO){
        transaction {
            Cars.insert{
                it[owner] = carDTO.owner
                it[mark] =carDTO.mark
                it[model] = carDTO.model
                it[engineDisplacement] = carDTO.engineDisplacement
                it[wheelDrive] = carDTO.wheelDrive
                it[year] = carDTO.year
                it[vinType] = carDTO.vinType
                it[vin] = carDTO.vin
            }
        }
    }

    fun delete(carDTO: CarDTO){
        transaction {
            Cars.deleteWhere { Cars.id eq carDTO.id }
        }
    }

    fun delete(carID: Int){
        transaction {
            Cars.deleteWhere { Cars.id eq carID }
        }
    }
}