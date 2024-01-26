package com.krts.bankprofile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "balance") val balance: Int
){
    constructor(username: String, balance: Int): this(0, username, balance)
}