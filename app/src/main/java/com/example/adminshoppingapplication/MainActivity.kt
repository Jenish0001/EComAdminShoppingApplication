package com.example.adminshoppingapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.adminshoppingapplication.Model.ProductModelData
import com.example.adminshoppingapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MainActivity : AppCompatActivity() {
    //    var img:String?=null
    var url: Uri? = null
    lateinit var blinding: ActivityMainBinding
    var list1 = arrayListOf<ModelData>()
    var stringList = arrayListOf<String>()
    var downloadUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        insertCategory()

        readData()

        categorySpinner()

//         categry add btn======
        categryAddBtn()
//         product details==============
        productInsert()


    }
// product detalis insert =====

    private fun productInsert() {

        blinding.uploadImg.setOnClickListener {
//             gallary picker=======

            gallaryupload()

        }
        blinding.insertBtn.setOnClickListener {

//            productInserData(productModelData)
//         upload Img in firebase storege
            updateImg()
        }

    }

//     update img in fire base in storege

    private fun updateImg() {

        var file = File(url.toString())
        var storage = Firebase.storage

        var storageReference = storage.reference.child(file.name)
        var UploadTask = storageReference.putFile(url!!)

        UploadTask.addOnSuccessListener { res ->

            storageReference.downloadUrl.addOnSuccessListener { uri ->

                if (uri != null) {

                    downloadUri = uri

                    var productModelData = ProductModelData(
                        blinding.enterProductNameTxt.text.toString(),
                        blinding.enterProductPriceTxt.text.toString(),
                        blinding.enterProductOfferTxt.text.toString(),
                        blinding.enterProductDiscTxt.text.toString(),
                        blinding.enterProductCategrayTxt.text.toString(),
                        downloadUri.toString()
                    )

                    productInserData(productModelData)
                }
            }




            Toast.makeText(this, "Sucees", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener { error ->

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()

            }

    }

    private fun gallaryupload() {

        var i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(i, 100)


    }

    private fun productInserData(productModelData: ProductModelData) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").push().setValue(productModelData)


//        list.clear()
//        Toast.makeText(this, "su", Toast.LENGTH_SHORT).show()


        var i = Intent(this, Home::class.java)
        startActivity(i)

    }


    //     catrgry add Btn method======
    private fun categryAddBtn() {

        blinding.categeryBtn.setOnClickListener {
            blinding.rv1.isVisible = true


        }
        blinding.arrowImg.setOnClickListener {

            blinding.rv1.isVisible = false

        }

    }

    //         categry Data Spineer

    private fun categorySpinner() {

        var arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, stringList)

        blinding.categorySpinner.adapter = arrayAdapter

//        val l1= resources.getStringArray(stringList.size)

        blinding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {}
                override fun onNothingSelected(p0: AdapterView<*>?) { }
            }
    }


//    categry Insert data =======

    private fun insertCategory() {

        blinding.btn.setOnClickListener {

            var dbHelper = ModelData(
                blinding.enterCategaryId.text.toString(),
                blinding.enterProductName.text.toString()
            )

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference
            databaseReference.child("categry").push().setValue(dbHelper)

            list1.clear()
            stringList.clear()

        }

    }


    // categry    read==========
    private fun readData() {

        list1.clear()
        stringList.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("categry").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children) {

                    var id = x.child("id").getValue().toString()
                    var category = x.child("pro").getValue().toString()

                    var data = ModelData(id, category)

                    var stringcategory = category

                    list1.add(data)

                    stringList.add(stringcategory)


                    Log.e("TAG", "onDataChange: $stringList")


                }

            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (100 == requestCode) {

            url = data?.data!!

            blinding.pimage.setImageURI(url)

        }

    }
}

// categry add class
class ModelData(val id: String, val pro: String) {}






