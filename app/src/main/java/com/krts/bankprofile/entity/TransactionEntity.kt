package com.krts.bankprofile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    @ColumnInfo(name="cuenta_ordenante")
    val cuenta_ordenante: Int,
    @ColumnInfo(name="cuenta_beneficiario")
    val cuenta_benefiriario: Int,
    @ColumnInfo(name="monto")
    val monto: Int,
) {
    constructor(
        cuenta_ordenante: Int,
        cuenta_benefiriario: Int,
        monto: Int): this(0, cuenta_ordenante, cuenta_benefiriario, monto)
}