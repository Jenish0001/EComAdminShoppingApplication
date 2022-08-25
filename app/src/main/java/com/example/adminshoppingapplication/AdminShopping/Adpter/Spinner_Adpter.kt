package com.example.adminshoppingapplication.AdminShopping.Adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.adminshoppingapplication.AdminShopping.loginscreen.MainActivity
import com.example.adminshoppingapplication.R

class Spinner_Adpter(val mainActivity: MainActivity ,val data: Array<String>) : BaseAdapter() {

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(p0: Int): Int {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }


        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var view = LayoutInflater.from(mainActivity).inflate(R.layout.spinner_item, p2, false)

            var text = view.findViewById<TextView>(R.id.textitem)

            text.setText(data[p0])

            return view

        }


    }
