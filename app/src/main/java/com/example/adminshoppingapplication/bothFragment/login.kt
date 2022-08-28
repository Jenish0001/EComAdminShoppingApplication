package com.example.adminshoppingapplication.bothFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayoutMediator

class login : AppCompatActivity() {

    val tabtitle= arrayListOf( "Admin","User")
    lateinit var blinding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(blinding.root)
        setuptablayout()


    }



//     set up  layout tab=======================

    private fun setuptablayout() {

        blinding.viewpager2.adapter= adpterlayout(this)
        TabLayoutMediator(blinding.tablayout,blinding.viewpager2){
            tab,position->
            tab.text=tabtitle[position]

        }.attach()

        for (i in 0..3)
        {
            val textView=LayoutInflater.from(this).inflate(R.layout.tab_title,null)
            as TextView

            blinding.tablayout.getTabAt(i)?.customView=textView
        }
    }



}