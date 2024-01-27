package com.krts.bankprofile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krts.bankprofile.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM TransactionEntity WHERE id= :id")
    fun getTransactionById(id: Int): TransactionEntity

    @Insert
    fun createTransaction(vararg transaction: TransactionEntity)
}