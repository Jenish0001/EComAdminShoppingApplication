package com.example.adminshoppingapplication.bothFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class userFragment : Fragment() {


    lateinit var logInBtn: Button
    lateinit var regesterTxt: TextView
    lateinit var EnteremailEdt: TextInputEditText
    lateinit var EnterpasswordEdt: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user, container, false)

        logInBtn = view.findViewById<Button>(R.id.logInBtn)
        regesterTxt = view.findViewById<TextView>(R.id.regesterTxt)
        EnteremailEdt = view.findViewById<TextInputEditText>(R.id.EnteremailEdt)
        EnterpasswordEdt = view.findViewById<TextInputEditText>(R.id.EnterpasswordEdt)

        loginBtn()

        regesterBtn()
        return view
    }

    private fun regesterBtn() {


    }

    private fun loginBtn() {

        logInBtn.setOnClickListener {

            var firebase = FirebaseAuth.getInstance()
            firebase.signInWithEmailAndPassword(
                EnteremailEdt.text.toString(),
                EnterpasswordEdt.text.toString()
            ).addOnSuccessListener { res->
                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                var i = Intent(requireActivity(), User_MainActivity::class.java)
                startActivity(i)
                requireActivity().finish()
            }
                .addOnFailureListener {
                    error->
                    Toast.makeText(requireActivity(), "${error.message}", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "signin: ${error.message}")
                }

        }

    }


}