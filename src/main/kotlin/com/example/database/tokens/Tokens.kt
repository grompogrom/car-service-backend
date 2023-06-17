package com.example.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens: Table("tokens") {
    private val token = Tokens.varchar("token", 36)
    private val user = Tokens.integer("user")

    fun insert(tokenDTO: TokenDTO){
        transaction {
            Tokens.insert{
                it[token] = tokenDTO.token
                it[user] = tokenDTO.userID
            }
        }
    }

    fun fetchToken(token: String): TokenDTO?{
        return try {
            transaction {
                val tokenModel = Tokens.select { Tokens.token.eq(token) }.single()
                TokenDTO(
                    token = tokenModel[Tokens.token],
                    userID = tokenModel[user]
                )
            }
        } catch (e: NoSuchElementException){
            null
        }
    }

    fun fetchToken(userID: Int): TokenDTO?{
        return try {
            transaction {
                val tokenModel = Tokens.select { Tokens.user.eq(userID) }.single()
                TokenDTO(
                    token = tokenModel[token],
                    userID = tokenModel[user]
                )
            }
        } catch (e: NoSuchElementException){
            null
        }
    }
}