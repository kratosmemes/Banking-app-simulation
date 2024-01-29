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
import androidx.cardview.widget.CardView
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
        val tvUserName                  :TextView = findViewById(R.id.tvUserName)
        val tvBalance                   :TextView = findViewById(R.id.tvBalance)
        val llLatestMovementsSent       :LinearLayout = findViewById(R.id.llLatestMovementsSent)
        val llLatestMovementsReceived   :LinearLayout = findViewById(R.id.llLatestMovementsReceived)
        val btnTransfer                 : Button = findViewById(R.id.btnTransfer)
        val btnSignOut                  : TextView = findViewById(R.id.tvSingOut)

        //Intent values
        val userFromIntent: String = intent.getStringExtra(Constants.USER_NAME).toString()
        tvUserName.text = intent.getStringExtra(Constants.USER_NAME)

        //Getting user data
        val connection = UserService().getConnection(this)
        val userDataFromDb: UserEntity? = connection.getUserByName(userFromIntent)
        tvBalance.text = "$${userDataFromDb?.balance.toString()}"

        //Transfer button
        btnTransfer.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            intent.putExtra(Constants.USER_NAME, userFromIntent)
            startActivity(intent)
            finish()
        }

        //Style for each programmatically inserted view in LinearLayout
        val amountViewLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        amountViewLayoutParams.setMargins(20, 3, 0, 0)

        /* --------------------------------------------------------------------------- */
        /* --------------------------- Latest transactions --------------------------- */
        /* --------------------------------------------------------------------------- */

        //Getting latest transactions from DB
        val transactionConnection = TransactionService().getConnection(this)
        val transactionsSent = transactionConnection.getTransactionsByCustomerIdSent(userDataFromDb!!._id).sortedByDescending { it.fecha }
        val transactionsReceived = transactionConnection.getTransactionsByCustomerIdRecived(userDataFromDb!!._id).sortedByDescending { it.fecha }
        val lastFiveTransactionsSent = transactionsSent.subList(0, transactionsSent.size).take(5)
        val lastFiveTransactionsReceived = transactionsReceived.subList(0, transactionsReceived.size).take(5)

        //Setting each transactions to latest LinearLayout widget
        lastFiveTransactionsSent.forEach{item->
            val card = CardView(this)
            val textViewAmount = TextView(this)
            val textViewDate = TextView(this)

            val date = Date(item.fecha).toString().substring(0, 19)

            textViewAmount.layoutParams = amountViewLayoutParams
            textViewAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textViewAmount.text = "$${item.monto}"
            llLatestMovementsSent.addView(textViewAmount)
        }

        lastFiveTransactionsReceived.forEach{item->
            val date = Date(item.fecha).toString().substring(0, 11)
            val textview = TextView(this)
            textview.layoutParams = amountViewLayoutParams
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textview.text = "$${item.monto}                       ${date}"
            llLatestMovementsReceived.addView(textview)
        }

        btnSignOut.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.USER_NAME, "")
            startActivity(intent)
            finish()
        }
    }
}

