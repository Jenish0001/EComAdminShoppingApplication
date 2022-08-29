package com.example.adminshoppingapplication.UserShopping.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.modelClass.DBCartInsert
import com.example.adminshoppingapplication.UserShopping.modelClass.DBcartRead
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DBcartAdpter(val cartFragment: FragmentActivity, val list: ArrayList<DBcartRead>) :
    RecyclerView.Adapter<DBcartAdpter.ViewData>() {
    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var offerTxt = itemView.findViewById<TextView>(R.id.offerTxt)
        var userItemImg = itemView.findViewById<ImageView>(R.id.userItemImg)
        var removeImg = itemView.findViewById<ImageView>(R.id.removeImg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(cartFragment).inflate(R.layout.cartitem, parent, false)
        return ViewData(view)

    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.nameTxt.text = list[position].pname
        holder.priceTxt.text = list[position].pprice
        holder.offerTxt.text = list[position].poffer
        Glide.with(cartFragment).load(list[position].downloadUrl).into(holder.userItemImg)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var dr = firebaseDatabase.reference
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

holder.removeImg.setOnClickListener {

    dr.child("AddCart").child(uid).child(list[position].key).removeValue()
//    list.clear()
}

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
