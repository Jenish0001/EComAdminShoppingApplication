package com.example.adminshoppingapplication.AdminShopping.AdminScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminshoppingapplication.AdminShopping.Adpter.Spinner_Adpter
import com.example.adminshoppingapplication.AdminShopping.Home
import com.example.adminshoppingapplication.AdminShopping.Model.ProductModelData
import com.example.adminshoppingapplication.AdminShopping.ModelData
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
    var data = arrayOf<String>("select")
    var downloadUri: Uri? = null
    lateinit var catename: String
     var cid: Int? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(blinding.root)

        readData()

//         product details==============
        productInsert()

        blinding.imageBtnBack.setOnClickListener {

            onBackPressed()
        }
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
                        catename,
                        downloadUri.toString(),
                        cid
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


    // categry    read==========
    private fun readData() {

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

//                Toast.makeText(this@MainActivity, "${data[position]}", Toast.LENGTH_SHORT).show()

                catename = data[position]
                cid=position+1

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

    fun setupSpinner(data: Array<String>) {
        var spinnerAdpter=Spinner_Adpter(this,data)
        blinding.catSppiner.adapter = spinnerAdpter
        spinnerAdpter.notifyDataSetChanged()
    }

}

// categry add class
//class ModelData(val id: String, val pro: String) {}