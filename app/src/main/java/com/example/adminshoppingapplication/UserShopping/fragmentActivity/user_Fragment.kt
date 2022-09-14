package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.Activity.wishList
import com.example.adminshoppingapplication.bothFragment.login
import com.example.adminshoppingapplication.databinding.FragmentUserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class user_Fragment() : Fragment() {

    lateinit var blnding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        blnding = FragmentUserProfileBinding.inflate(layoutInflater)

        logoutBtnonClick()

        logimage()

        blnding.favriteTxt.setOnClickListener {

            var i=Intent(activity,wishList::class.java)
            activity?.startActivity(i)

        }

        return blnding.root

    }

    private fun logimage() {

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var u= user?.photoUrl
        var uname=user?.displayName

        Glide.with(requireActivity()).load(u).into(blnding.userImg)
        blnding.userid.text=uname


    }

    fun logoutBtnonClick() {
        blnding.logoutImg.setOnClickListener {
            logout()
        }
        blnding.logoutTxt.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
// fire base log out========
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        var intent = Intent(activity, login::class.java)
        startActivity(intent)
        requireActivity().finish()

// google log out ==========
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(requireActivity().getString(R.string.client))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        googleSignInClient.signOut()


    }




}