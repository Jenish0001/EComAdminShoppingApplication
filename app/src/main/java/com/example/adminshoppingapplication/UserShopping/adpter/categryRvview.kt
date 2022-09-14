package com.example.adminshoppingapplication.UserShopping.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.fragmentActivity.categryFragment.Companion.sbinding
import com.example.adminshoppingapplication.UserShopping.modelClass.ModelReadcatagry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class categryRvview(
    val categryFragment: FragmentActivity?,
    val name: Array<String>,
    val imge: Array<Int>
) : RecyclerView.Adapter<categryRvview.viewData>() {

    var no = arrayOf(0,1,2,3,4,5)
    var list = arrayListOf<ModelReadcatagry>()

    class viewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtView = itemView.findViewById<TextView>(R.id.txtView)
        var setImageCV = itemView.findViewById<ImageView>(R.id.setImageCV)
        var cvview = itemView.findViewById<CardView>(R.id.cvview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewData {
        var view =
            LayoutInflater.from(categryFragment).inflate(R.layout.categryshowitem, parent, false)
        return categryRvview.viewData(view)
    }

    override fun onBindViewHolder(holder: viewData, position: Int) {

        holder.txtView.text = name[position]
        holder.setImageCV.setImageResource(imge[position])
        readdata(0)
        holder.cvview.setOnClickListener {

            readdata(no.get(position))
        }
    }

    override fun getItemCount(): Int {

        return name.size
    }

    private fun readdata(get: Int) {

        list.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference

        firebaseReference.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var poffer = x.child("poffer").getValue().toString()
                    var pdes = x.child("pdes").getValue().toString()
                    var pcat = x.child("pcat").getValue().toString()
                    var downloadUrl = x.child("url").getValue().toString()
                    var key = x.key.toString()
                    var cid = x.child("cid").getValue().toString()

                    if (get == cid.toInt()) {

                        var m1 =
                            ModelReadcatagry(
                                pname,
                                pprice,
                                poffer,
                                pdes,
                                pcat,
                                downloadUrl,
                                key,
                                cid
                            )
                        list.add(m1)


                    }
                    showrecycleView(list)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun showrecycleView(list: ArrayList<ModelReadcatagry>) {

        var adpter = showRvview(categryFragment, list)
        var layouet = LinearLayoutManager(categryFragment)
        layouet.orientation = LinearLayoutManager.HORIZONTAL
        sbinding.showRvview.adapter = adpter
        sbinding.showRvview.layoutManager = layouet

    }

}