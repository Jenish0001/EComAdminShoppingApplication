package com.example.adminshoppingapplication.UserShopping.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.AdminShopping.Model.ModelReadData
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_MainActivity

class searchAdpter(val userMainactivity: FragmentActivity?, val list: ArrayList<ModelReadData>):RecyclerView.Adapter<searchAdpter.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView)
    {


        var userItemImg = itemView.findViewById<ImageView>(R.id.userItemImg)
        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var offerTxt = itemView.findViewById<TextView>(R.id.offerTxt)
        var rvviewitem = itemView.findViewById<RelativeLayout>(R.id.rvviewitem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchAdpter.ViewData {

        var view = LayoutInflater.from(userMainactivity).inflate(R.layout.useritem, parent, false)
        return ViewData(view)


    }

    override fun onBindViewHolder(holder: searchAdpter.ViewData, position: Int) {

        holder.nameTxt.text = list[position].pname
        holder.priceTxt.text = list[position].pprice
        holder.offerTxt.text = list[position].poffer
        Glide.with(userMainactivity!!).load(list[position].downloadUrl).into(holder.userItemImg)


    }

    override fun getItemCount(): Int {
        return list.size
    }
}