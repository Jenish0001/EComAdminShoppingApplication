package com.example.adminshoppingapplication.loginscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminshoppingapplication.MainActivity
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    lateinit var blind: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login_screen)
        blind = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(blind.root)
        signInMethhod()
    }

    private fun signInMethhod() {

        blind.logInBtn.setOnClickListener {
            loginprocess(blind.EnteremailEdt.text.toString(),blind.EnterpasswordEdt.text.toString())
        }

    }

    private fun loginprocess(email: String, password: String) {

        var firebaseAuth= FirebaseAuth.getInstance()

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                rep->
            Log.e("TAG", "loginprocess: $rep")

            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }.addOnFailureListener {
                error->
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "loginprocess: ${error.message}")
        }

    }



}