package com.example.database.records

import com.example.features.records.RecordGetResponseRemote
import java.time.LocalDateTime

data class RecordDTO(
    val id: Int,
    val user: Int,
    val car: Int,
    val datetime: LocalDateTime
){
    fun toRecordsGetResponseRemote(): RecordGetResponseRemote {
        return RecordGetResponseRemote(
            id = id,
            car =  car,
            datetime = datetime.toString()
        )
    }
}
