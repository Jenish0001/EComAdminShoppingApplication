package com.example.adminshoppingapplication.UserShopping.adpter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.AdminShopping.AdminScreen.updateItem_acitivity
import com.example.adminshoppingapplication.AdminShopping.Adpter.DataAdapter
import com.example.adminshoppingapplication.AdminShopping.Model.ModelReadData
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_Item_Show_Activity
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.homeFrgment

class userAdpter(val homeFrgment: FragmentActivity, val list: ArrayList<ModelReadData>) :
    RecyclerView.Adapter<userAdpter.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userItemImg = itemView.findViewById<ImageView>(R.id.userItemImg)
        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var offerTxt = itemView.findViewById<TextView>(R.id.offerTxt)
        var rvviewitem = itemView.findViewById<RelativeLayout>(R.id.rvviewitem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view = LayoutInflater.from(homeFrgment).inflate(R.layout.useritem, parent, false)
        return ViewData(view)

    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.nameTxt.text = list[position].pname
        holder.priceTxt.text = list[position].pprice
        holder.offerTxt.text = list[position].poffer
        Glide.with(homeFrgment).load(list[position].downloadUrl).into(holder.userItemImg)


        holder.rvviewitem.setOnClickListener {

            var pname=list[position].pname
            var pprice=list[position].pprice
            var poffer=list[position].poffer
            var pcat=list[position].pcat
            var pdes=list[position].pdes
            var downloadUrl=list[position].downloadUrl

            var i= Intent(homeFrgment, User_Item_Show_Activity::class.java)


            i.putExtra("n1",pname)
            i.putExtra("n2",pprice)
            i.putExtra("n3",poffer)
            i.putExtra("n4",pcat)
            i.putExtra("n5",pdes)
            i.putExtra("n6",downloadUrl)

            homeFrgment.startActivity(i)

        }

    }


    override fun getItemCount(): Int {
        return list.size
    }
}