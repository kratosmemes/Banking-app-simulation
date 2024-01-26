package com.krts.bankprofile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krts.bankprofile.dao.UserDao
import com.krts.bankprofile.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}