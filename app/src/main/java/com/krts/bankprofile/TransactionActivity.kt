package com.krts.bankprofile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krts.bankprofile.entity.TransactionEntity
import com.krts.bankprofile.service.TransactionService
import com.krts.bankprofile.service.UserService
import java.sql.Date

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
        val etCuentaBeneficiario: EditText? = findViewById(R.id.etBeneficiario)
        val etMonto: EditText? = findViewById(R.id.etMonto)
        val btnCancel: Button = findViewById(R.id.btnCancel)
        val btnContinue: Button = findViewById(R.id.btnContinue)
        val btnSignOut: TextView = findViewById(R.id.tvSignOut)

        //
        tvCuentaOrdenante.text = userFromIntent

        btnContinue.setOnClickListener {
            val beneficiario = userConnection.getUserByName(etCuentaBeneficiario?.text.toString())
            val ordenante = userConnection.getUserByName(userFromIntent)



            if(etCuentaBeneficiario?.text.toString().equals("") || etMonto?.text.toString().equals("")){
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
            }else if(beneficiario == null){
                Toast.makeText(this, "El usuario beneficiario no existe", Toast.LENGTH_SHORT).show()
            }else if(ordenante.balance < etMonto?.text.toString().toInt()){
                Toast.makeText(this, "No cuentas con los fondos suficientes para la transacción", Toast.LENGTH_SHORT).show()
            }else {
                val newTransaction = TransactionEntity(
                    ordenante._id,
                    beneficiario._id,
                    etMonto?.text.toString().toInt(),
                    System.currentTimeMillis()
                )

                transactionConnection.createTransaction(newTransaction)
                userConnection.updateMonto(
                    (ordenante.balance) - (etMonto?.text.toString().toInt()),
                    ordenante._id
                )
                userConnection.updateMonto(
                    (beneficiario.balance) + (etMonto?.text.toString().toInt()), beneficiario._id
                )

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(Constants.USER_NAME, userFromIntent)
                startActivity(intent)
                finish()
            }

        }

        btnCancel.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(Constants.USER_NAME, userFromIntent)
            startActivity(intent)
            finish()
        }

        btnSignOut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.USER_NAME, "")
            startActivity(intent)
            finish()
        }
    }
}
