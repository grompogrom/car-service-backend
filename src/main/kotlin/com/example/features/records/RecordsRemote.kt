package com.example.features.records

import com.example.database.records.RecordDTO
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class RecordGetResponseRemote(
    val id: Int,
    val car: Int,
    val datetime: String
)

@Serializable
data class RecordListGetResponseRemote(
    val records: List<RecordGetResponseRemote>
)

@Serializable
data class RecordsAddReceiveRemote(
    val car: Int,
    val datetime: String
){
    fun toRecordsDTO(recordID: Int, userID: Int): RecordDTO {
        return RecordDTO(
            id = recordID,
            user = userID,
            car = car,
            datetime = LocalDateTime.parse(datetime)
        )
    }
}

@Serializable
data class RecordsDeleteReceiveRemote(
    val id: Int
)