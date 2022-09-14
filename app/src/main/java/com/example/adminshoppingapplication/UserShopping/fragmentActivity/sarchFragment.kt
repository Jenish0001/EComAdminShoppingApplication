package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adminshoppingapplication.AdminShopping.Model.ModelReadData
import com.example.adminshoppingapplication.UserShopping.adpter.searchAdpter
import com.example.adminshoppingapplication.databinding.FragmentSarchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class sarchFragment() : Fragment() {


    var list = arrayListOf<ModelReadData>()
    lateinit var adapter: ArrayAdapter<*>

    lateinit var blinding: FragmentSarchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        blinding = FragmentSarchBinding.inflate(layoutInflater)

        readdata()

        searchview()
        return blinding.root
    }

    private fun searchview() {

//        adapter = ArrayAdapter<ModelReadData>(this, , list)

    }


    private fun setuprv(list: ArrayList<ModelReadData>) {

        var useradpter = searchAdpter(activity, list)
        var layoutManager = GridLayoutManager(activity, 2)
        blinding.recyclerView.adapter = useradpter
        blinding.recyclerView.layoutManager = layoutManager

    }

    private fun readdata() {

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

                    var m1 = ModelReadData(pname, pprice, poffer, pdes, pcat, downloadUrl, key, cid)

                    list.add(m1)

                }
                setuprv(list)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}