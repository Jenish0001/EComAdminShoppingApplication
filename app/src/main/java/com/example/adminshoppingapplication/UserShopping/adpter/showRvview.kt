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
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.modelClass.ModelReadcatagry

class showRvview(val activity: FragmentActivity?, val list: ArrayList<ModelReadcatagry>) :
    RecyclerView.Adapter<showRvview.viewData>() {
    class viewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userItemImg = itemView.findViewById<ImageView>(R.id.userItemImg)
        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var description = itemView.findViewById<TextView>(R.id.description)
        var offerTxt = itemView.findViewById<TextView>(R.id.offerTxt)
        var rvviewitem = itemView.findViewById<RelativeLayout>(R.id.rvviewitem)
        var likeBtn = itemView.findViewById<ImageView>(R.id.likeBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewData {

        var view =
            LayoutInflater.from(activity).inflate(R.layout.setcategryitem, parent, false)
        return viewData(view)

    }

    override fun onBindViewHolder(holder: viewData, position: Int) {
        holder.nameTxt.text = list[position].pname
        holder.priceTxt.text = list[position].pprice
        holder.offerTxt.text = list[position].poffer
        holder.description.text = list[position].pdes
        Glide.with(activity!!).load(list[position].downloadUrl).into(holder.userItemImg)


    }

    override fun getItemCount(): Int {

        return list.size
    }
}