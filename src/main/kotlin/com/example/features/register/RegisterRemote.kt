package com.example.features.register

import com.example.database.users.UserDTO
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

@Serializable
data class RegisterReceiveRemote (
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val drivingStage: String,
    val birthday: String,
    val password: String
){
    fun toUserDTO(id: Int): UserDTO{
        return UserDTO(
            id = id,
            password = password,
            firstName = firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            lastName = lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            email = email,
            phone = if(phone.isBlank()) null else phone,
            drivingStage = LocalDate.parse(drivingStage),
            birthday = LocalDate.parse(birthday),
            regDate = LocalDate.now()
        )
    }
}

@Serializable
data class RegisterResponseRemote(
    val token: String
)