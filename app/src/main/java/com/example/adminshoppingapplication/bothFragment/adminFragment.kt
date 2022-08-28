package com.example.adminshoppingapplication.bothFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.adminshoppingapplication.AdminShopping.Home
import com.example.adminshoppingapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class adminFragment : Fragment() {

    lateinit var EnteremailEdt: TextInputEditText
    lateinit var EnterpasswordEdt: TextInputEditText
    lateinit var passwordEdt: TextInputLayout
    lateinit var logInBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_admin, container, false)

        EnteremailEdt = view.findViewById<TextInputEditText>(R.id.EnteremailEdt)
        EnterpasswordEdt = view.findViewById<TextInputEditText>(R.id.EnterpasswordEdt)
        passwordEdt = view.findViewById<TextInputLayout>(R.id.passwordEdt)
        logInBtn = view.findViewById<Button>(R.id.logInBtn)

        signInMethhod()

        return view
    }

    private fun signInMethhod() {

        logInBtn.setOnClickListener {
            loginprocess(
                EnteremailEdt.text.toString(),
                EnterpasswordEdt.text.toString()
            )
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun loginprocess(email: String, password: String) {

        var firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener { rep ->
            Log.e("TAG", "loginprocess: $rep")

            var intent = Intent(requireActivity(), Home::class.java)
            startActivity(intent)

            requireActivity().finish()
        }.addOnFailureListener { error ->



            EnteremailEdt.setTextColor(Color.parseColor("#A61E1E"))
            EnteremailEdt.setError("Enter Valid Admin Email ")

            EnterpasswordEdt.setTextColor(Color.parseColor("#A61E1E"))
            EnterpasswordEdt.setError("Enter valid admin password ")


            Toast.makeText(requireActivity(), "fail", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "loginprocess: ${error.message}")

        }

    }

}