package com.krts.bankprofile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="cuenta_ordenante")
    val cuentaOrdenante: Int,
    @ColumnInfo(name="cuenta_beneficiario")
    val cuentaBenefiriario: Int,
    @ColumnInfo(name="monto")
    val monto: Int,
    @ColumnInfo(name="fecha")
    val fecha: Long
) {
    constructor(
        cuentaOrdenante: Int,
        cuentaBeneficiario: Int,
        monto: Int,
        fecha: Long): this(0, cuentaOrdenante, cuentaBeneficiario, monto, fecha)
}