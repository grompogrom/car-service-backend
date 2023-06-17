package com.example.database.avatars

import com.example.database.users.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Avatars: Table("avatars") {
    val user = Avatars.integer("user").references(Users.id)
    val img = Avatars.binary("img")
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(user)

    fun insert(avatarDTO: AvatarDTO){
        transaction {
            insert {
                it[user] = avatarDTO.user
                it[img] = avatarDTO.img
            }
        }
    }

    fun fetch(userID: Int): AvatarDTO?{
        return try {
            transaction {
                val avatar = select { Avatars.user eq userID }.single()
                AvatarDTO(
                    user = avatar[user],
                    img = avatar[img]
                )
            }
        } catch (e: Exception){
            null
        }
    }

    fun update(avatarDTO: AvatarDTO){
        transaction {
            update ({ Avatars.user.eq(avatarDTO.user) }) {
                it[Avatars.user] = avatarDTO.user
                it[Avatars.img] = avatarDTO.img
            }
        }
    }
}