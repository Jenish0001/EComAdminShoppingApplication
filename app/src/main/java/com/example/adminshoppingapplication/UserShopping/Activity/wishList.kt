package com.example.adminshoppingapplication.UserShopping.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshoppingapplication.UserShopping.adpter.wishlistAdpter
import com.example.adminshoppingapplication.UserShopping.modelClass.ModelReadcatagry
import com.example.adminshoppingapplication.databinding.ActivityWishListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class wishList : AppCompatActivity() {

    lateinit var blinding: ActivityWishListBinding
    var list = arrayListOf<ModelReadcatagry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        blinding = ActivityWishListBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        wishlist()

        backonbtn()
    }

    private fun backonbtn() {

        blinding.backarrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun wishlist() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

        firebaseReference.child("mywishlist").child(uid)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()


                    for (x in snapshot.children) {
                        var pname = x.child("pname").getValue().toString()
                        var pprice = x.child("pprice").getValue().toString()
                        var poffer = x.child("poffer").getValue().toString()
                        var pdes = x.child("pdes").getValue().toString()
                        var pcat = x.child("pcat").getValue().toString()
                        var downloadUrl = x.child("downloadUrl").getValue().toString()
                        var quontaty = x.child("quontaty").getValue().toString()
                        var key = x.key.toString()


                        var m1 = ModelReadcatagry(
                            pname,
                            pprice,
                            poffer,
                            pdes,
                            pcat,
                            downloadUrl,
                            key, quontaty
                        )


                        list.add(m1)


                    }
                    setupRV(list)
                }


                override fun onCancelled(error: DatabaseError) {

                }
            })

    }

    private fun setupRV(list: ArrayList<ModelReadcatagry>) {

        var adpter = wishlistAdpter(this, list)
        var layoutManager = GridLayoutManager(this, 2)
        blinding.rvview.adapter = adpter
        blinding.rvview.layoutManager = layoutManager

    }
}