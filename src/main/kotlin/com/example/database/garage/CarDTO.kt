package com.example.database.garage

import com.example.features.garage.CarResponseRemote
import java.time.LocalDate

data class CarDTO (
    val id: Int,
    val owner: Int,
    val mark: String,
    val model: String,
    val engineDisplacement: Double,
    val wheelDrive: String,
    val year: LocalDate,
    val vinType: String,
    val vin: String
){
    fun toCarResponseRemote(): CarResponseRemote{
        return CarResponseRemote(
            id = id,
            mark = mark,
            model = model,
            engineDisplacement = engineDisplacement,
            year = year.toString(),
            vinType = vinType,
            wheelDrive = wheelDrive,
            vin = vin
        )
    }
}