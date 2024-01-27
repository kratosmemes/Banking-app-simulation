package com.krts.bankprofile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krts.bankprofile.dao.TransactionDao
import com.krts.bankprofile.dao.UserDao
import com.krts.bankprofile.entity.TransactionEntity
import com.krts.bankprofile.entity.UserEntity

@Database(entities = [UserEntity::class, TransactionEntity::class], version = 5)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDAO(): TransactionDao
}