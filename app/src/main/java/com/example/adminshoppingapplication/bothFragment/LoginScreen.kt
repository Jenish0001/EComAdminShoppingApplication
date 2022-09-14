package com.example.adminshoppingapplication.bothFragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_MainActivity
import com.example.adminshoppingapplication.databinding.ActivityLoginScreenBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginScreen : AppCompatActivity() {

    private val RC_SIGN_IN = 1
    lateinit var blind: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login_screen)
        blind = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(blind.root)

        signInMethhod()
        google()

    }

    private fun google() {

        blind.googleBtn.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.client)))
                .requestEmail()
                .build()
            var googleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, RC_SIGN_IN)

        }

    }

    private fun signInMethhod() {

        blind.registerBtn.setOnClickListener {
            signin(
                blind.EnteremailEdt.text.toString(),
                blind.EnterpasswordEdt.text.toString(),
                blind.EnterPhoneNudEdt.text.toString()
            )
            var i= Intent(this,User_MainActivity::class.java)
            startActivity(i)
        }

    }

    fun signin(email: String, password: String, phone: String) {

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener { error ->
                Log.e("TAG", "signin: ${error.message}")
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)  {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
//                old code==========================

                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                var account = result?.signInAccount
                loginwithCredantail(account?.idToken.toString())


            }

        }
    }

    private fun loginwithCredantail(idToken: String) {


        val crd = GoogleAuthProvider.getCredential(idToken, null)

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(crd).addOnSuccessListener { res ->
            var i = Intent(this, User_MainActivity::class.java)
            startActivity(i)

        }.addOnFailureListener { error ->

            Log.e("TAG", "loginwithCredantail: ${error.message}")
        }

    }


}