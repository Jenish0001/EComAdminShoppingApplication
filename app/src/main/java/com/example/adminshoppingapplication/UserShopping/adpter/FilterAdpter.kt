package com.example.adminshoppingapplication.UserShopping.adpter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.sarchFragment

class FilterAdpter(val sarchFragment: FragmentActivity, val fill: ArrayList<String>) :
    RecyclerView.Adapter<FilterAdpter.ViewData>() {

    private val name: ArrayList<String>?=null

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv=itemView.findViewById<TextView>(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view = LayoutInflater.from(sarchFragment).inflate(R.layout.filterite, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.tv.text= name?.get(position)

    }

    override fun getItemCount(): Int {
        return fill.size

    }
    @SuppressLint("NotifyDataSetChanged")
    fun filteradpter(name:ArrayList<String>)
    {
//        name=fill
//        notifyDataSetChanged()
    }
}