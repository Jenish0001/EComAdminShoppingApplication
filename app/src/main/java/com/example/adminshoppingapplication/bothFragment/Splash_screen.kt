package com.example.adminshoppingapplication.AdminShopping.AdminScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.adminshoppingapplication.AdminShopping.Home
import com.example.adminshoppingapplication.bothFragment.LoginScreen
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_MainActivity
import com.example.adminshoppingapplication.bothFragment.login
import com.google.firebase.auth.FirebaseAuth

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser

        Handler().postDelayed({

            if (user != null) {

                var intent = Intent(this, User_MainActivity::class.java)
                startActivity(intent)

            } else {

                var intent = Intent(this, login::class.java)
                startActivity(intent)
            }
            finish()

        }, 300)

    }
}