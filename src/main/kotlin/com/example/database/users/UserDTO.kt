package com.example.database.users

import com.example.features.account.AccountRemote
import java.time.LocalDate

data class UserDTO (
    val id: Int,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val drivingStage: LocalDate,
    val birthday: LocalDate,
    val regDate: LocalDate
){
    fun toAccountRemote(): AccountRemote{
        return AccountRemote(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone ?: "",
            drivingStage = drivingStage.toString(),
            birthday = birthday.toString()
        )
    }
}
