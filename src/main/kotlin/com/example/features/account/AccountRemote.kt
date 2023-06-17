package com.example.features.account

import com.example.database.avatars.AvatarDTO
import com.example.database.users.UserDTO
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

@Serializable
data class AccountRemote(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val drivingStage: String,
    val birthday: String,
){
    fun toUserDTO(id: Int, password: String, regDate: LocalDate):UserDTO{
        return UserDTO(
            id = id,
            firstName = firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            lastName = lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            email = email,
            phone = phone.ifBlank { null },
            drivingStage = LocalDate.parse(drivingStage),
            birthday = LocalDate.parse(birthday),
            password = password,
            regDate = regDate
        )
    }
}

@Serializable
data class UpdatePhotoReceiveRemote(
    val img: Array<Byte>
){
    fun toAvatarDTO(userID: Int): AvatarDTO {
        return AvatarDTO(
            user = userID,
            img = img.toByteArray()
        )
    }
}

@Serializable
data class UserPhotoResponseRemote(
    val img: ByteArray
)
