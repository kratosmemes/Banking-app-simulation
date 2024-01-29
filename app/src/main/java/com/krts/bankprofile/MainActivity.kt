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

        //Database connector
        val userConnection: UserDao = UserService().getConnection(this)

        btnContinue.setOnClickListener {
            val userExists: UserEntity = userConnection.getUserByName(etUserName.text.toString())
            if(userExists == null){
                userConnection.createUser(UserEntity(
                    username = etUserName.text.toString(),
                    balance = 10000
                ))
                Toast.makeText(this, "User created successfully", Toast.LENGTH_LONG).show()
                sendToHomeView(etUserName)
            }else{
                Toast.makeText(this, "User found", Toast.LENGTH_SHORT).show()
                sendToHomeView(etUserName)
            }
        }
    }

    //Switch user to home screen
    private fun sendToHomeView(etUserName: TextView){
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(Constants.USER_NAME, etUserName.text.toString())
        startActivity(intent)
        finish()
    }
}

