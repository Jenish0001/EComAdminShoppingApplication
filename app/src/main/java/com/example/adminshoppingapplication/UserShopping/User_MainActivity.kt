package com.example.adminshoppingapplication.UserShopping

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.CartFragment
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.homeFrgment
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.sarchFragment
import com.example.adminshoppingapplication.databinding.ActivityUserMainBinding

class User_MainActivity : AppCompatActivity() {

    lateinit var mainblinding: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainblinding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(mainblinding.root)

//        bottomnavigationbar()

    }

    private fun bottomnavigationbar() {

        val homefragment = homeFrgment()
        val serchfragment = sarchFragment()
        val cardfragment = CartFragment(this)

        loadFragment(homefragment)

        mainblinding.bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.tab_home -> loadFragment(homefragment)
                R.id.tab_category -> loadFragment(serchfragment)
                R.id.tab_search -> loadFragment(serchfragment)
                R.id.tab_cart -> loadFragment(cardfragment)
                R.id.tab_user -> loadFragment(serchfragment)


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


