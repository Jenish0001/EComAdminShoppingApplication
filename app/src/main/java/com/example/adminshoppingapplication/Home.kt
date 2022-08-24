package com.example.adminshoppingapplication

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshoppingapplication.Adpter.DataAdapter
import com.example.adminshoppingapplication.Model.ModelReadData
import com.example.adminshoppingapplication.databinding.ActivityHome2Binding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class Home : AppCompatActivity() {

    lateinit var blinding: ActivityHome2Binding
    var list1 = arrayListOf<ModelData>()
    var stringList = arrayListOf<String>()
    var data = arrayOf<String>("select")

    var list = arrayListOf<ModelReadData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(blinding.root)

        readdata()

        productbtn()

        blinding.categoryBtn.setOnClickListener {
            categrybtn()

        }
    }

    private fun categrybtn() {

        var dialog = Dialog(this)
        dialog.setContentView(R.layout.categryitem)
        dialog.show()

        var enterCategaryId = dialog.findViewById<TextInputEditText>(R.id.enterCategaryId)
        var enterProductName = dialog.findViewById<TextInputEditText>(R.id.enterProductName)
        var categorySpinner = dialog.findViewById<Spinner>(R.id.categorySpinner)
        var btn = dialog.findViewById<Button>(R.id.btn)
        var arrowImg = dialog.findViewById<ImageView>(R.id.arrowImg)

        readDataCategry(categorySpinner)
        arrowImg.setOnClickListener {

            dialog.dismiss()
        }

        btn.setOnClickListener {

            var dbHelper = ModelData(
                enterCategaryId.text.toString(),
                enterProductName.text.toString()
            )

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference
            databaseReference.child("categry").push().setValue(dbHelper)

            list1.clear()
            stringList.clear()
            dialog.dismiss()

        }

    }

    private fun readDataCategry(categorySpinner: Spinner) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

         categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(this@Home, "${data[position]}", Toast.LENGTH_SHORT).show()
            }
        }


        databaseReference.child("categry").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list1.clear()
                stringList.clear()
                data = emptyArray()

                for (x in snapshot.children) {

                    var id = x.child("id").getValue().toString()
                    var category = x.child("pro").getValue().toString()

                    var categry = ModelData(id, category)


                    list1.add(categry)
                    data += x.child("pro").getValue().toString()
                }
                setupSpinner(data, categorySpinner)
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@Home, "Error", Toast.LENGTH_SHORT).show()

            }
        })

    }

    fun setupSpinner(data: Array<String>, categorySpinner: Spinner) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        categorySpinner.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()
    }


    //    insert item product
    private fun productbtn() {

        blinding.productBtn.setOnClickListener {

            var i = Intent(this, MainActivity::class.java)
            startActivity(i)

        }
    }

    //   read recycler view
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
                    var key = x.key.toString()

                    var m1 = ModelReadData(pname, pprice, poffer, pdes, pcat, downloadUrl, key)

                    list.add(m1)

                }
                setupRV(list)
            }

            override fun onCancelled(error: DatabaseError) {

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

// categry add class
class ModelData(val id: String, val pro: String) {}
