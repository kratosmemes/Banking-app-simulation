package com.krts.bankprofile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.krts.bankprofile.dao.UserDao
import com.krts.bankprofile.entity.UserEntity
import com.krts.bankprofile.service.UserService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Widgets
        val btnContinue: Button = findViewById(R.id.btnContinue)
        val etUserName: EditText = findViewById(R.id.etUserName)

        //Database Conector
        val conection: UserDao = UserService().obtenerConexion(this)

        btnContinue.setOnClickListener {
            val userExists:UserEntity? = conection.getUserByName(etUserName.text.toString())
            if(userExists == null){
                println("Guarda y luego entra")
                conection.createUser(UserEntity(
                    username = etUserName.text.toString(),
                    balance = 10000
                ))
                Toast.makeText(this, "Usuario creado con exito", Toast.LENGTH_LONG).show()
                sendToHomeView(etUserName)
            }else{
                Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show()
                sendToHomeView(etUserName)
            }
        }
    }


    //Send user to home screen
    private fun sendToHomeView(etUserName: TextView){
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(Constants.USER_NAME, etUserName.text.toString())
        startActivity(intent)
        finish()
    }
}

