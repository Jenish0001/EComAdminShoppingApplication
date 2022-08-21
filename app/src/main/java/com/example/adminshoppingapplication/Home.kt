package com.example.adminshoppingapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshoppingapplication.Adpter.DataAdapter
import com.example.adminshoppingapplication.Model.ModelReadData
import com.example.adminshoppingapplication.databinding.ActivityHome2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class Home : AppCompatActivity() {

    lateinit var blinding: ActivityHome2Binding
    var list = arrayListOf<ModelReadData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(blinding.root)

        readdata()
    }


    private fun readdata() {

        list.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference

        firebaseReference.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var poffer = x.child("poffer").getValue().toString()
                    var pdes = x.child("pdes").getValue().toString()
                    var pcat = x.child("pcat").getValue().toString()
                    var downloadUrl = x.child("url").getValue().toString()
                    var key=x.key.toString()

                    var m1 = ModelReadData(pname, pprice, poffer, pdes, pcat,downloadUrl,key)

                    list.add(m1)

//                    list.clear()
                }
                setupRV(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setupRV(list: ArrayList<ModelReadData>) {

        var adapter = DataAdapter(this, list)
        var layoutManager = LinearLayoutManager(this)
        blinding.rvView.adapter = adapter
        blinding.rvView.layoutManager = layoutManager
    }

}

class ModelImg(val img: Uri) {}