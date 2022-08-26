package com.example.adminshoppingapplication.AdminShopping.AdminScreen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.AdminShopping.Adpter.Home_Spinner_Adpter
import com.example.adminshoppingapplication.AdminShopping.Home
import com.example.adminshoppingapplication.AdminShopping.Model.ProductModelData
import com.example.adminshoppingapplication.AdminShopping.ModelData
import com.example.adminshoppingapplication.databinding.ActivityUpdateItemAcitivityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class updateItem_acitivity : AppCompatActivity() {

    var id1: Int? = null
    var id: String? = null
    var url: Uri? = null
    var list1 = arrayListOf<ModelData>()
    var stringList = arrayListOf<String>()
    var data = arrayOf<String>("select")
    var categary: String? = null
    var cid: Int? = null


    lateinit var blinding: ActivityUpdateItemAcitivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        blinding = ActivityUpdateItemAcitivityBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        blinding.arrowImg.setOnClickListener {

            onBackPressed()
        }


        var pname = intent.getStringExtra("n1")
        var pprice = intent.getStringExtra("n2")
        var poffer = intent.getStringExtra("n3")
        var pcat = intent.getStringExtra("n4")
        var pdes = intent.getStringExtra("n5")
        var downloadimage = intent.getStringExtra("n6")
        id = intent.getStringExtra("n8")


//        Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()

        id1 = id!!.toInt()

        blinding.enterProductNametxt.setText(pname)
        blinding.enterProductPrice.setText(pprice)
        blinding.enterProductOffer.setText(poffer)
        blinding.enterProductDisc.setText(pdes)

        Glide.with(this).load(downloadimage).centerCrop().into(blinding.pimage)



        readDataCategry()

        blinding.uploadImg.setOnClickListener {

            gallaryupload()

        }

        blinding.insertBtn.setOnClickListener {

            updateData(downloadimage.toString())
            var i = Intent(this, Home::class.java)
            startActivity(i)

        }
    }

    private fun readDataCategry() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        blinding.catSppiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categary = data[position]
                cid = position
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

                    Log.e("TAG", "onDataChange: $stringList")

                }
                setupSpinner(data)
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@updateItem_acitivity, "Error", Toast.LENGTH_SHORT).show()

            }
        })

    }

    fun setupSpinner(data: Array<String>) {

        val arrayAdapter = Home_Spinner_Adpter(this, data)
        blinding.catSppiner.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()

        blinding.catSppiner.setSelection(id1!!)
    }

    private fun updateImageToStorage() {

        var file = File(url.toString())
        var storage = Firebase.storage

        var storageReference = storage.reference.child(file.name)
        var UploadTask = storageReference.putFile(url!!)


        UploadTask.addOnSuccessListener { res ->

            storageReference.downloadUrl.addOnSuccessListener { uri ->
                var temp = uri
                updateData1(temp.toString())
                var i = Intent(this, Home::class.java)
                startActivity(i)
            }
            Toast.makeText(this, "Sucees", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { error ->

            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateData1(temp: String) {

        var key = intent.getStringExtra("n7")

        var productModelData = ProductModelData(
            blinding.enterProductNametxt.text.toString(),
            blinding.enterProductPrice.text.toString(),
            blinding.enterProductOffer.text.toString(),
            blinding.enterProductDisc.text.toString(),
            categary!!,
            temp,
            cid
        )

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").child(key!!).setValue(productModelData)

    }

    private fun updateData(temp: String) {

        var key = intent.getStringExtra("n7")

        var productModelData = ProductModelData(
            blinding.enterProductNametxt.text.toString(),
            blinding.enterProductPrice.text.toString(),
            blinding.enterProductOffer.text.toString(),
            blinding.enterProductDisc.text.toString(),
            categary!!,
            temp,
            cid
        )
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").child(key!!).setValue(productModelData)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (100 == requestCode) {

            url = data?.data!!

            blinding.pimage.setImageURI(url)
            updateImageToStorage()
        }

    }

    private fun gallaryupload() {

        var i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(i, 100)

    }

}
