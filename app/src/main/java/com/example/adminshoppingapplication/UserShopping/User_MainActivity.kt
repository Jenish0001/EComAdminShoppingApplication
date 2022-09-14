package com.example.adminshoppingapplication.UserShopping

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.*
import com.example.adminshoppingapplication.databinding.ActivityUserMainBinding

class User_MainActivity : AppCompatActivity() {

    lateinit var blinding: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        bottomnavigationbar()

    }

    private fun bottomnavigationbar() {

        val homefragment = homeFrgment()
        val serchfragment = sarchFragment()
        val cardfragment = CartFragment()
        val categor = categryFragment()
        val user = user_Fragment()

        loadFragment(homefragment)

        blinding.bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.tab_home -> loadFragment(homefragment)
                R.id.tab_category -> loadFragment(categor)
                R.id.tab_search -> loadFragment(serchfragment)
                R.id.tab_cart -> loadFragment(cardfragment)
                R.id.tab_user -> loadFragment(user)
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) =

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.wrapper, fragment)
            commit()
        }

    fun chageActivity(activity: Activity)
    {

        var i=Intent(this,activity::class.java)
        startActivity(i)

    }
}


