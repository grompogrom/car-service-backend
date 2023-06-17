package com.example.utils

import com.example.features.account.AccountRemote
import com.example.features.garage.AddCarReceiveRemote
import com.example.features.records.RecordsAddReceiveRemote
import com.example.features.register.RegisterReceiveRemote
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

fun String.isValidEmail(): Boolean = true

fun String.isValidPhone(): Boolean = true

fun String.isValidName(): Boolean = true

fun String.isValidWheelDrive(): Boolean{
    return this in arrayOf("FRONT", "REAR", "FOUR_WHEEL")
}

fun String.isValidDate(): Boolean {
    return try {
        LocalDate.parse(this)
        true
    }
    catch (e: DateTimeParseException){
        false
    }

}

fun String.isValidDateTime(): Boolean {
    return try {
        LocalDateTime.parse(this)
        true
    }
    catch (e: DateTimeParseException){
        false
    }

}

fun String.isValidPassword(): Boolean = this.length >= 8

fun RegisterReceiveRemote.isValidFields(): Boolean{
    return with(this){
        firstName.isValidName() &&
                lastName.isValidName() &&
                email.isValidEmail() &&
                phone?.isValidPhone() ?: true &&
                drivingStage.isValidDate() &&
                birthday.isValidDate() &&
                password.isValidPassword()
    }
}

fun AddCarReceiveRemote.isValidFields(): Boolean{
    return with(this){
        engineDisplacement in 0.0..12.0 &&
                wheelDrive.isValidWheelDrive() &&
                year.isValidDate() &&
                LocalDate.parse(year).year <= LocalDate.now().year &&
                vin.length in arrayOf(17, 12, 11)
    }
}

fun AccountRemote.isValidFields(): Boolean{
    return with(this){
        firstName.isValidName() &&
                lastName.isValidName() &&
                email.isValidEmail() &&
                phone.isValidPhone() &&
                drivingStage.isValidDate() &&
                birthday.isValidDate()
    }
}

fun RecordsAddReceiveRemote.isValidFields(): Boolean{
    return with(this){
        datetime.isValidDateTime()
    }
}