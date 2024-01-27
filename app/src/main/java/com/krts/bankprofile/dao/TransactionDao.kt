package com.krts.bankprofile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krts.bankprofile.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM TransactionEntity WHERE id= :id")
    fun getTransactionById(id: Int): TransactionEntity

    @Query("SELECT * FROM transactionentity WHERE cuenta_ordenante= :idOrdenante")
    fun getTransactionsByCustomerIdSent(idOrdenante: Int): List<TransactionEntity>

    @Query("SELECT * FROM transactionentity WHERE cuenta_beneficiario= :idBeneficiario")
    fun getTransactionsByCustomerIdRecived(idBeneficiario: Int): List<TransactionEntity>

    @Query("SELECT * FROM transactionentity")
    fun getAll(): List<TransactionEntity>

    @Insert
    fun createTransaction(vararg transaction: TransactionEntity)
}