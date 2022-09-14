package com.example.adminshoppingapplication.UserShopping.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.Activity.wishList
import com.example.adminshoppingapplication.UserShopping.modelClass.DBcartRead
import com.example.adminshoppingapplication.UserShopping.modelClass.ModelReadcatagry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class wishlistAdpter(val wishList: wishList, val list: ArrayList<ModelReadcatagry>):
    RecyclerView.Adapter<wishlistAdpter.viewData>() {

    class viewData(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var userItemImg = itemView.findViewById<ImageView>(R.id.userItemImg)
        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var description = itemView.findViewById<TextView>(R.id.description)
        var offerTxt = itemView.findViewById<TextView>(R.id.offerTxt)
        var rvviewitem = itemView.findViewById<RelativeLayout>(R.id.rvviewitem)
        var likeBtn = itemView.findViewById<ImageView>(R.id.likeBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewData {

        var view = LayoutInflater.from(wishList).inflate(R.layout.wishlistitem, parent, false)
        return viewData(view)
    }

    override fun onBindViewHolder(holder: viewData, position: Int) {
        holder.nameTxt.text = list[position].pname
        holder.priceTxt.text = list[position].pprice
        holder.offerTxt.text = list[position].poffer
        holder.description.text = list[position].pdes
        holder.likeBtn.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var dr = firebaseDatabase.reference
            var firebaseAuth = FirebaseAuth.getInstance()
            var user = firebaseAuth.currentUser
            var uid = user?.uid.toString()
            dr.child("mywishlist").child(uid).child(list[position].key).removeValue()
            list.clear()
        }
        Glide.with(wishList).load(list[position].downloadUrl).into(holder.userItemImg)
    }

    override fun getItemCount(): Int {

        return list.size
    }
}