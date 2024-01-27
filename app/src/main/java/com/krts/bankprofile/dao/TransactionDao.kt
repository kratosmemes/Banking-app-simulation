package com.krts.bankprofile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krts.bankprofile.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM TransactionEntity WHERE _id=:_id")
    fun getTransactionById(_id: Int): TransactionEntity

    @Insert
    fun createTransaction(vararg transaction: TransactionEntity)
}