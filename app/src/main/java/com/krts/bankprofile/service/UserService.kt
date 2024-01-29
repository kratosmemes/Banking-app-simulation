package com.krts.bankprofile.service

import android.content.Context
import androidx.room.Room
import com.krts.bankprofile.AppDatabase
import com.krts.bankprofile.dao.UserDao

class UserService {
    fun getConnection(context: Context): UserDao {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "padillasbank"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        return db.userDao()
    }
}