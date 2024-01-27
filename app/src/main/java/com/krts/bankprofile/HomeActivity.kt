package com.krts.bankprofile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.krts.bankprofile.dao.TransactionDao
import com.krts.bankprofile.dao.UserDao
import com.krts.bankprofile.entity.TransactionEntity
import com.krts.bankprofile.entity.UserEntity
import com.krts.bankprofile.service.TransactionService
import com.krts.bankprofile.service.UserService
import java.lang.IllegalStateException
import java.util.Date

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        //Widgets
        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvBalance: TextView = findViewById(R.id.tvBalance)
        val llLastestMovementsSent: LinearLayout = findViewById(R.id.llLastestMovementsSent)
        val llLastestMovementsRecibed: LinearLayout = findViewById(R.id.llLastestMovementsRecived)
        val btnTransferir: Button = findViewById(R.id.btnTransferir)
        val btnSignOut: TextView = findViewById(R.id.tvSingOut)

        val fragmentManager = supportFragmentManager

        val intflater = this.layoutInflater
        val userFromIntent: String = intent.getStringExtra(Constants.USER_NAME).toString()

        tvUserName.text = intent.getStringExtra(Constants.USER_NAME)

        //Getting user data
        val conection = UserService().obtenerConexion(this)
        val userDataFromDb: UserEntity? = conection.getUserByName(userFromIntent)
        tvBalance.text = "$${userDataFromDb?.balance.toString()}"

        //Tranferir boton
        btnTransferir.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            intent.putExtra(Constants.USER_NAME, userFromIntent)
            startActivity(intent)
            finish()
        }

        //Setting last movements
        val transactionConnection = TransactionService().obtenerConexion(this)
        val transactionsSent =
            transactionConnection.getTransactionsByCustomerIdSent(userDataFromDb!!._id)
                .sortedByDescending { it.fecha }
        val transactionsRecived =
            transactionConnection.getTransactionsByCustomerIdRecived(userDataFromDb!!._id)
                .sortedByDescending { it.fecha }
        val transactions2 = transactionConnection.getAll().sortedByDescending { it.fecha }
        val lastFiveTransactionsSent = transactionsSent.subList(0, transactionsSent.size).take(5)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(20, 3, 0, 0)

        lastFiveTransactionsSent.forEach{item->
            val date = Date(item.fecha)
            val textview: TextView = TextView(this)
            textview.layoutParams = layoutParams
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textview.setText("$${item.monto} ----- ${date}")
            llLastestMovementsSent.addView(textview)
        }

        val lastFiveTransactionsRecived = transactionsRecived.subList(0, transactionsRecived.size).take(5)

        lastFiveTransactionsRecived.forEach{item->
            val date = Date(item.fecha)
            val textview: TextView = TextView(this)
            textview.layoutParams = layoutParams
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textview.setText("$${item.monto} ----- ${date}")
            llLastestMovementsRecibed.addView(textview)
        }

        btnSignOut.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.USER_NAME, "")
            startActivity(intent)
            finish()
        }
    }
}

