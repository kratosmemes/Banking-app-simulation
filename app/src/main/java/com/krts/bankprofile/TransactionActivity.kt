package com.krts.bankprofile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.krts.bankprofile.entity.TransactionEntity
import com.krts.bankprofile.service.TransactionService
import com.krts.bankprofile.service.UserService

class TransactionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_activity)

        //Base de datos
        val transactionConnection = TransactionService().obtenerConexion(this)
        val userConnection = UserService().obtenerConexion(this)

        //Intent variable
        val userFromIntent: String = intent.getStringExtra(Constants.USER_NAME).toString()



        //Widgets
        val tvCuentaOrdenante: TextView = findViewById(R.id.etOrdenante)
        val etCuentaBeneficiario: EditText = findViewById(R.id.etBeneficiario)
        val etMonto: EditText = findViewById(R.id.etMonto)
        val btnCancel: Button = findViewById(R.id.btnCancel)
        val btnContinue: Button = findViewById(R.id.btnContinue)

        //
        tvCuentaOrdenante.text = userFromIntent

        btnContinue.setOnClickListener {

            //Getting data from db
            val beneficiario = userConnection.getUserByName(etCuentaBeneficiario.text.toString())
            val ordenante = userConnection.getUserByName(userFromIntent)

            val newTransaction = TransactionEntity(
                beneficiario._id,
                ordenante._id,
                etMonto.text.toString().toInt()
            )

            transactionConnection.createTransaction(newTransaction)
            userConnection.updateMonto((ordenante.balance) - (etMonto.text.toString().toInt()), ordenante._id)
            userConnection.updateMonto((beneficiario.balance) + (etMonto.text.toString().toInt()), beneficiario._id)

            println(userConnection.getAll().toString())
            /*val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(Constants.USER_NAME, userFromIntent)
            startActivity(intent)
            finish()*/
        }
    }
}
