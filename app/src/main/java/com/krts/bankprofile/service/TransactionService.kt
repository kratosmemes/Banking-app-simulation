package com.krts.bankprofile.service

import android.content.Context
import androidx.room.Room
import com.krts.bankprofile.AppDatabase
import com.krts.bankprofile.dao.TransactionDao
import com.krts.bankprofile.dao.UserDao

class TransactionService {
    fun obtenerConexion(context: Context): TransactionDao {
        val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "padillasbank"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        return db.transactionDAO()
    }
}