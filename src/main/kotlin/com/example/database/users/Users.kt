package com.example.database.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {
    val id = Users.integer("id").autoIncrement()
    private val password = Users.varchar("password", 25)
    private val firstName = Users.varchar("first_name", 15)
    private val lastName = Users.varchar("last_name", 20)
    private val email = Users.varchar("email", 30)
    private val phone = Users.varchar("phone", 12).nullable()
    private val drivingStage = Users.date("driving_stage")
    private val birthday = Users.date("birthday")
    private val regDate = Users.date("reg_date")
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id, name =  "PK_users_id")

    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert {
                it[password] = userDTO.password
                it[firstName] = userDTO.firstName
                it[lastName] = userDTO.lastName
                it[email] = userDTO.email
                it[phone] = userDTO.phone
                it[drivingStage] = userDTO.drivingStage
                it[birthday] = userDTO.birthday
                it[regDate] = userDTO.regDate
            }
        }
    }
    fun fetchUser(userID: Int): UserDTO?{
        return try {
                transaction {
                    val user = Users.select {  Users.id.eq(userID)}.single()
                    UserDTO(
                        id = user[Users.id],
                        password = user[password],
                        firstName = user[firstName],
                        lastName = user[lastName],
                        email = user[email],
                        phone = user[phone],
                        drivingStage = user[drivingStage],
                        birthday = user[birthday],
                        regDate = user[regDate]
                    )
                }
            } catch (exception: NoSuchElementException){
                null
            }
    }

    fun fetchUser(userEmail: String): UserDTO?{
        return try {

        transaction {
            val user = Users.select {  email.eq(userEmail)}.single()
             UserDTO(
                id = user[Users.id],
                password = user[password],
                firstName = user[firstName],
                lastName = user[lastName],
                email = user[email],
                phone = user[phone],
                drivingStage = user[drivingStage],
                birthday = user[birthday],
                regDate = user[regDate]
            )
        }
        } catch (exception: NoSuchElementException){
             null
        }
    }

    fun fetchAllUsers(): List<UserDTO>{

        return  transaction{
            val users = Users.selectAll()
            users.map { user ->
                UserDTO(
                    id = user[Users.id],
                    password = user[password],
                    firstName = user[firstName],
                    lastName = user[lastName],
                    email = user[email],
                    phone = user[phone],
                    drivingStage = user[drivingStage],
                    birthday = user[birthday],
                    regDate = user[regDate]
                )
            }
        }
    }

    fun updateUser(userID: Int, userDTO: UserDTO): UserDTO{
        return transaction {
            Users.update({Users.id eq userID}) {
                it[password] = userDTO.password
                it[firstName] = userDTO.firstName
                it[lastName] = userDTO.lastName
                it[email] = userDTO.email
                it[phone] = userDTO.phone
                it[drivingStage] = userDTO.drivingStage
                it[birthday] = userDTO.birthday
                it[regDate] = userDTO.regDate
            }
            val user = Users.select { Users.id.eq(userID) }.single()
            UserDTO(
                id = user[Users.id],
                password = user[password],
                firstName = user[firstName],
                lastName = user[lastName],
                email = user[email],
                phone = user[phone],
                drivingStage = user[drivingStage],
                birthday = user[birthday],
                regDate = user[regDate]
            )
        }
    }
}