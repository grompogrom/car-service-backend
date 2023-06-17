package com.example.features.garage

import com.example.database.garage.CarDTO
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GarageResponseRemote(
    val cars: List<CarResponseRemote>
)

@Serializable
data class CarResponseRemote(
    val id: Int,
    val mark: String,
    val model: String,
    val engineDisplacement: Double,
    val wheelDrive: String,
    val year: String,
    val vinType: String,
    val vin: String
)

@Serializable
data class AddCarReceiveRemote(
    val mark: String,
    val model: String,
    val engineDisplacement: Double,
    val wheelDrive: String,
    val year: String,
    val vinType: String,
    val vin: String
){
    fun toCarDTO(ownerID: Int): CarDTO{
        return CarDTO(
            id = 0,
            owner = ownerID,
            mark = mark,
            model = model,
            engineDisplacement = engineDisplacement,
            wheelDrive = wheelDrive,
            year = LocalDate.parse(year),
            vinType = vinType,
            vin = vin
        )
    }
}


@Serializable
data class DeleteCarReceiveRemote(
    val id: Int
)