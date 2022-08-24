package com.example.adminshoppingapplication.Adpter

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.Home
import com.example.adminshoppingapplication.Model.ModelReadData
import com.example.adminshoppingapplication.Model.ProductModelData
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.updateItem_acitivity
import com.google.firebase.database.FirebaseDatabase


class DataAdapter(
    val home: Home, val list: ArrayList<ModelReadData>
) : RecyclerView.Adapter<DataAdapter.ViewData>() {
    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pname = itemView.findViewById<TextView>(R.id.pname)
        var pprice = itemView.findViewById<TextView>(R.id.pprice)
        var poffer = itemView.findViewById<TextView>(R.id.poffer)
        var img = itemView.findViewById<ImageView>(R.id.img)
        var cvDelet = itemView.findViewById<CardView>(R.id.cvDelet)
        var cvupdate = itemView.findViewById<CardView>(R.id.cvupdate)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        var view = LayoutInflater.from(home).inflate(R.layout.item, parent, false)
        return ViewData(view)

    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.pname.text = list[position].pname
        holder.pprice.text = list[position].pprice
        holder.poffer.text = list[position].poffer
        Glide.with(home).load(list[position].downloadUrl).centerCrop().into(holder.img)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var dr = firebaseDatabase.reference

        holder.cvDelet.setOnClickListener {

            val builder1: AlertDialog.Builder = AlertDialog.Builder(home)

            builder1.setMessage("Are you sure you want to delet ?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Delet",

                DialogInterface.OnClickListener { dialog, id ->
                    dr.child("Product").child(list.get(position).key).removeValue()
                    list.clear()
                    dialog.cancel()
                })

            builder1.setNegativeButton(
                "cancle",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            val alert11: AlertDialog = builder1.create()
            alert11.show()


        }

        holder.cvupdate.setOnClickListener {
            var pname=list[position].pname
            var pprice=list[position].pprice
            var poffer=list[position].poffer
            var pcat=list[position].pcat
            var pdes=list[position].pdes
            var downloadUrl=list[position].downloadUrl
//            var cid=list[position].

            var key=list[position].key

            var i=Intent(home,updateItem_acitivity::class.java)
            i.putExtra("n1",pname)
            i.putExtra("n2",pprice)
            i.putExtra("n3",poffer)
            i.putExtra("n4",pcat)
            i.putExtra("n5",pdes)
            i.putExtra("n6",downloadUrl)
            i.putExtra("n7",key)
            home.startActivity(i)
        }
    }


    override fun getItemCount(): Int {

        return list.size
    }


}



