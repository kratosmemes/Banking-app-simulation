package com.krts.bankprofile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.krts.bankprofile.dao.TransactionDao
import com.krts.bankprofile.dao.UserDao
import com.krts.bankprofile.entity.TransactionEntity
import com.krts.bankprofile.entity.UserEntity
import com.krts.bankprofile.service.UserService
import java.lang.IllegalStateException

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        //Widgets
        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvBalance: TextView = findViewById(R.id.tvBalance)
        val llLastestMovements: LinearLayout = findViewById(R.id.llLastestMovements)
        val btnTransferir: Button = findViewById(R.id.btnTransferir)

        val fragmentManager = supportFragmentManager

        val intflater = this.layoutInflater
        val userFromIntent: String = intent.getStringExtra(Constants.USER_NAME).toString()

        tvUserName.text = intent.getStringExtra(Constants.USER_NAME)

        //Getting user data
        val conection = UserService().obtenerConexion(this)
        val userDataFromDb: UserEntity? = conection.getUserByName(userFromIntent)
        tvBalance.text = userDataFromDb?.balance.toString()

        //Tranferir boton
        btnTransferir.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            intent.putExtra(Constants.USER_NAME, userFromIntent)
            startActivity(intent)
            finish()
        }

        //Setting last movements
        val textview: TextView = TextView(this)
        textview.setText("holaaa")


    }
}

