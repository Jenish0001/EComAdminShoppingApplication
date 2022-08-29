package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.User_MainActivity
import com.example.adminshoppingapplication.UserShopping.adpter.DBcartAdpter
import com.example.adminshoppingapplication.UserShopping.modelClass.DBcartRead
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class CartFragment(val userMainactivity: User_MainActivity) : Fragment() {

    lateinit var rvview: RecyclerView
    var list = arrayListOf<DBcartRead>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_cart, container, false)

        rvview = view.findViewById<RecyclerView>(R.id.rvview)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

        firebaseReference.child("AddCart").child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (x in snapshot.children) {
                        var pname = x.child("pname").getValue().toString()
                        var pprice = x.child("pprice").getValue().toString()
                        var poffer = x.child("poffer").getValue().toString()
                        var pdes = x.child("pdes").getValue().toString()
                        var pcat = x.child("pcat").getValue().toString()
                        var downloadUrl = x.child("downloadimage").getValue().toString()
                        var key = x.key.toString()

                        var m1 = DBcartRead(pname, pprice, poffer, pdes, pcat, downloadUrl,key)

                        list.add(m1)

                    }
                    setupRV(list)
                }


                override fun onCancelled(error: DatabaseError) {

                }
            })

        return view

    }

    private fun setupRV(list: ArrayList<DBcartRead>) {

        var adpter = DBcartAdpter(userMainactivity, list)
        var layoutManager = LinearLayoutManager(userMainactivity)
         rvview.adapter =adpter
        rvview.layoutManager = layoutManager

    }
}