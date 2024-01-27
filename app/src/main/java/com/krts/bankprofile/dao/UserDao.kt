package com.krts.bankprofile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krts.bankprofile.entity.UserEntity

@Dao
interface  UserDao {

    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE username LIKE :username")
    fun getUserByName(username: String): UserEntity

    @Query("UPDATE UserEntity SET balance= :newMonto WHERE _id= :id")
    fun updateMonto(newMonto: Int, id: Int)

    @Insert
    fun createUser(vararg user: UserEntity)
}