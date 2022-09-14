package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.adpter.DBcartAdpter
import com.example.adminshoppingapplication.UserShopping.modelClass.DBcartRead
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class CartFragment() : Fragment() {

    private var minteger: Int = 0
    lateinit var rvview: RecyclerView
    lateinit var checkOutFinal: Button
    lateinit var ttext: TextView


    var list = arrayListOf<DBcartRead>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_cart, container, false)

        rvview = view.findViewById<RecyclerView>(R.id.rvview)
        checkOutFinal = view.findViewById<Button>(R.id.checkOutFinal)
        ttext = view.findViewById<TextView>(R.id.ttext)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

        firebaseReference.child("AddCart").child(uid)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    minteger = 0

                    for (x in snapshot.children) {
                        var pname = x.child("pname").getValue().toString()
                        var pprice = x.child("pprice").getValue().toString()
                        var poffer = x.child("poffer").getValue().toString()
                        var pdes = x.child("pdes").getValue().toString()
                        var pcat = x.child("pcat").getValue().toString()
                        var downloadUrl = x.child("downloadimage").getValue().toString()
                        var quontaty = x.child("quontaty").getValue().toString()
                        var key = x.key.toString()


                        var m1 = DBcartRead(
                            pname,
                            pprice,
                            poffer,
                            pdes,
                            pcat,
                            downloadUrl,
                            key, quontaty
                        )

                        minteger = (pprice.toInt() * quontaty.toInt()) + minteger
                        list.add(m1)
                        ttext.text = minteger.toString()

                    }
                    setupRV(list)
                }


                override fun onCancelled(error: DatabaseError) {

                }
            })

//        bottomsheet()

        return view

    }

    private fun bottomsheet() {

        checkOutFinal.setOnClickListener {

            val dialog = BottomSheetDialog(requireActivity())
            val view = layoutInflater.inflate(R.layout.bottomsheet, null)

            val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)
            val idTVCourseTracks = view.findViewById<TextView>(R.id.idTVCourseTracks)



            btnClose.setOnClickListener {

                dialog.dismiss()

            }

            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()

        }

    }

    private fun setupRV(list: ArrayList<DBcartRead>) {

        var adpter = DBcartAdpter(activity, list)
        var layoutManager = LinearLayoutManager(activity)
        rvview.adapter = adpter
        rvview.layoutManager = layoutManager

    }
}