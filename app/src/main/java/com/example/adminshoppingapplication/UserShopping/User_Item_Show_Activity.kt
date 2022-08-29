package com.example.adminshoppingapplication.UserShopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.CartFragment
import com.example.adminshoppingapplication.UserShopping.modelClass.DBCartInsert
import com.example.adminshoppingapplication.databinding.ActivityUserItemShowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class User_Item_Show_Activity : AppCompatActivity() {

    lateinit var blinding: ActivityUserItemShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding = ActivityUserItemShowBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        var pname = intent.getStringExtra("n1")
        var pprice = intent.getStringExtra("n2")
        var poffer = intent.getStringExtra("n3")
        var pcat = intent.getStringExtra("n4")
        var pdes = intent.getStringExtra("n5")
        var downloadimage = intent.getStringExtra("n6")
        backArrow()

        Glide.with(this).load(downloadimage).into(blinding.cvImg)
        blinding.upname.text = pname
        blinding.uppriceTxt.text = pprice
        blinding.upofferTxt.text = poffer
        blinding.desTxt.text = pdes


        blinding.cartBtbn.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference

            var firebaseAuth = FirebaseAuth.getInstance()
            var user = firebaseAuth.currentUser
            var uid = user?.uid.toString()

            var carddata = DBCartInsert(
                pname, pprice, pcat, pdes, downloadimage,poffer
            )

            databaseReference.child("AddCart").child(uid).push().setValue(carddata)

            var i= Intent(this,User_MainActivity::class.java)
            startActivity(i)


        }


    }

    private fun backArrow() {

        blinding.backArrowImg.setOnClickListener {
            onBackPressed()
        }
    }


}