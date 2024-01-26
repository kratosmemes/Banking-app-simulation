package com.krts.bankprofile

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krts.bankprofile.entity.UserEntity
import com.krts.bankprofile.service.UserService

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        //Widgets
        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvBalance: TextView = findViewById(R.id.tvBalance)

        val userFromIntent: String = intent.getStringExtra(Constants.USER_NAME).toString()

        tvUserName.text = intent.getStringExtra(Constants.USER_NAME)

        //Getting user data
        val conection = UserService().obtenerConexion(this)
        val userDataFromDb: UserEntity? = conection.getUserByName(userFromIntent)
        tvBalance.text = userDataFromDb?.balance.toString()
        println("******************************************************")
        println(userFromIntent)
        println(userDataFromDb)
        println("******************************************************")
        Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT)
    }
}