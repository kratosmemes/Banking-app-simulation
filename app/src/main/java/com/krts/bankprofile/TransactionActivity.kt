package com.krts.bankprofile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krts.bankprofile.entity.TransactionEntity
import com.krts.bankprofile.entity.UserEntity
import com.krts.bankprofile.service.TransactionService
import com.krts.bankprofile.service.UserService
import java.sql.Date

class TransactionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_activity)

        //Widgets
        val tvOrderingAccount:      TextView = findViewById(R.id.tvOrderingAccountNotChangeable)
        val etBeneficiaryAccount:   EditText? = findViewById(R.id.etBeneficiaryAccount)
        val etBalance:              EditText? = findViewById(R.id.etMonto)
        val btnCancel:              Button = findViewById(R.id.btnCancel)
        val btnContinue:            Button = findViewById(R.id.btnContinue)
        val btnSignOut:             TextView = findViewById(R.id.tvSignOut)

        //Database connections
        val transactionConnection = TransactionService().getConnection(this)
        val userConnection = UserService().getConnection(this)

        //Intent variable
        val userFromIntent: String = intent.getStringExtra(Constants.USER_NAME).toString()
        tvOrderingAccount.text = userFromIntent

        //Transaction functionality
        btnContinue.setOnClickListener {
            val beneficiary = userConnection.getUserByName(etBeneficiaryAccount?.text.toString())
            val ordering  = userConnection.getUserByName(userFromIntent)

            if(
                etBeneficiaryAccount?.text.toString() == "" || etBalance?.text.toString() == ""){
                Toast.makeText(this, "You must fill all the blanks", Toast.LENGTH_SHORT).show()
            }else if(beneficiary == null){
                Toast.makeText(this, "Beneficiary user doest not exists", Toast.LENGTH_SHORT).show()
            }else if(ordering.balance < etBalance?.text.toString().toInt()){
                Toast.makeText(this, "You do not have sufficient funds", Toast.LENGTH_SHORT).show()
            }else {

                //New transaction object
                val newTransaction = TransactionEntity(
                    ordering._id,
                    beneficiary._id,
                    etBalance?.text.toString().toInt(),
                    System.currentTimeMillis()
                )

                try {
                    transactionConnection.createTransaction(newTransaction)
                    userConnection.updateMonto(
                        (ordering.balance) - (etBalance?.text.toString().toInt()),
                        ordering._id
                    )
                    userConnection.updateMonto(
                        (beneficiary.balance) + (etBalance?.text.toString().toInt()),
                        beneficiary._id
                    )
                }catch (ex: Exception){
                    Toast.makeText(this, "There has been an error with the transaction", Toast.LENGTH_SHORT).show()
                }

                //Intent to switch to Home Screen
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
