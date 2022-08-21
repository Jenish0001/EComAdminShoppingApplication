package com.example.adminshoppingapplication.Adpter

import android.app.Dialog
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
import com.example.adminshoppingapplication.R
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
            dr.child("Product").child("${list.get(position).key}").removeValue()
            list.clear()


        }
        holder.cvupdate.setOnClickListener {

            product(position)

        }


    }

    private fun product(position: Int) {


        var dialog = Dialog(home)
        dialog.setContentView(R.layout.itemdialog)

        dialog.show()


        var enterProductName = dialog.findViewById<EditText>(R.id.enterProductNametxt)
        var enterProductPrice = dialog.findViewById<EditText>(R.id.enterProductPrice)
        var enterProductOffer= dialog.findViewById<EditText>(R.id.enterProductOffer)
        var enterProductCategray = dialog.findViewById<EditText>(R.id.enterProductCategray)
        var enterProductDisc= dialog.findViewById<EditText>(R.id.enterProductDisc)
        var pimage = dialog.findViewById<ImageView>(R.id.pimage)
        var insertBtn = dialog.findViewById<Button>(R.id.insertBtn)


        enterProductName.setText(list.get(position).pname)
        enterProductPrice.setText(list.get(position).pprice)
        enterProductOffer.setText(list.get(position).poffer)
        enterProductCategray.setText(list.get(position).pcat)
        enterProductDisc.setText(list.get(position).pdes)

        Glide.with(home).load(list.get(position).downloadUrl).centerCrop().into(pimage)


        insertBtn.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var dr = firebaseDatabase.reference

            var m=list[position].downloadUrl
            var m1 = ModelRead(
                enterProductName.text.toString(),
                enterProductPrice.text.toString(),
                enterProductCategray.text.toString(),
                enterProductOffer.text.toString(),
                enterProductDisc.text.toString(),
               m
            )

                    dr.child("Product").child("${list.get(position).key}").setValue(m1)


            dialog.dismiss()

            list.clear()
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }

}
