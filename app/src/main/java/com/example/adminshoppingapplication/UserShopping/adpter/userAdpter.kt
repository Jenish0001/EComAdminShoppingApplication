package com.example.adminshoppingapplication.UserShopping.adpter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.AdminShopping.Model.ModelReadData
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_Item_Show_Activity
import com.example.adminshoppingapplication.UserShopping.modelClass.MyWishModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class userAdpter(val homeFrgment: FragmentActivity?, val list: ArrayList<ModelReadData>) :
    RecyclerView.Adapter<userAdpter.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userItemImg = itemView.findViewById<ImageView>(R.id.userItemImg)
        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var description = itemView.findViewById<TextView>(R.id.description)
        var offerTxt = itemView.findViewById<TextView>(R.id.offerTxt)
        var rvviewitem = itemView.findViewById<RelativeLayout>(R.id.rvviewitem)
        var likeBtn = itemView.findViewById<ImageView>(R.id.likeBtn)
        var unlikeBtn = itemView.findViewById<ImageView>(R.id.unlikeBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view = LayoutInflater.from(homeFrgment).inflate(R.layout.useritem, parent, false)
        return ViewData(view)

    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.nameTxt.text = list[position].pname
        holder.priceTxt.text = list[position].pprice
        holder.offerTxt.text = list[position].poffer
        holder.description.text = list[position].pdes
        Glide.with(homeFrgment!!).load(list[position].downloadUrl).into(holder.userItemImg)
        var i = 0
        holder.unlikeBtn.setOnClickListener {
            i = 1
            if(i==1)
            {
                holder.likeBtn.isVisible = true

            }

            holder.likeBtn.setOnClickListener {


                holder.likeBtn.isVisible = false

            }
            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference

            var firebaseAuth = FirebaseAuth.getInstance()
            var user = firebaseAuth.currentUser
            var uid = user?.uid.toString()

            var carddata = MyWishModel(
                list[position].cid,
                list[position].pcat,
                list[position].pdes,
                list[position].poffer,
                list[position].pprice,
                list[position].downloadUrl,
                list[position].pname,
            )
            databaseReference.child("mywishlist").child(uid).push().setValue(carddata)
        }

        holder.rvviewitem.setOnClickListener {


            var pname = list[position].pname
            var pprice = list[position].pprice
            var poffer = list[position].poffer
            var pcat = list[position].pcat
            var pdes = list[position].pdes
            var downloadUrl = list[position].downloadUrl

            var i = Intent(homeFrgment, User_Item_Show_Activity::class.java)


            i.putExtra("n1", pname)
            i.putExtra("n2", pprice)
            i.putExtra("n3", poffer)
            i.putExtra("n4", pcat)
            i.putExtra("n5", pdes)
            i.putExtra("n6", downloadUrl)

            homeFrgment.startActivity(i)


        }

    }


    override fun getItemCount(): Int {
        return list.size
    }
}