package com.krts.bankprofile.service

import android.content.Context
import androidx.room.Room
import com.krts.bankprofile.AppDatabase
import com.krts.bankprofile.dao.TransactionDao

class TransactionService {
    fun obtenerConexion(context: Context): TransactionDao {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.javaObjectType,
            "padillasbank"
        ).allowMainThreadQueries().build()
    return db.transactionDAO()
    }
}