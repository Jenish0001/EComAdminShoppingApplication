package com.example.adminshoppingapplication.UserShopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.databinding.ActivityUserItemShowBinding

class User_Item_Show_Activity : AppCompatActivity() {

    lateinit var blinding:ActivityUserItemShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding=ActivityUserItemShowBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        var pname = intent.getStringExtra("n1")
        var pprice = intent.getStringExtra("n2")
        var poffer = intent.getStringExtra("n3")
        var pcat = intent.getStringExtra("n4")
        var pdes = intent.getStringExtra("n5")
        var downloadimage = intent.getStringExtra("n6")



    }


}