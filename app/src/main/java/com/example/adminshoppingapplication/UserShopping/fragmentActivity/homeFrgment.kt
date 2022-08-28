package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshoppingapplication.AdminShopping.Model.ModelReadData
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.adpter.userAdpter
import com.example.adminshoppingapplication.bothFragment.login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFrgment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var favriteImg: ImageView
    var list = arrayListOf<ModelReadData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home_frgment, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        favriteImg = view.findViewById<ImageView>(R.id.favriteImg)

//        setuprv(list)
        readdata()
        logout()

        return view
    }

    private fun logout() {

        favriteImg.setOnClickListener {

            var firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            var intent = Intent(requireActivity(), login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setuprv(list: ArrayList<ModelReadData>) {

        var useradpter = userAdpter(requireActivity(), list)
        var layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = useradpter
        recyclerView.layoutManager = layoutManager

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