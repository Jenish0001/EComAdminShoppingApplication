package com.example.adminshoppingapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.Model.ModelReadData
import com.example.adminshoppingapplication.Model.ProductModelData
import com.example.adminshoppingapplication.databinding.ActivityUpdateItemAcitivityBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class updateItem_acitivity : AppCompatActivity() {

    var url: Uri? = null
    var downloadUri: Uri? = null
    lateinit var list: ArrayList<ModelReadData>
    var list1 = arrayListOf<ModelData>()
    var stringList = arrayListOf<String>()
    var data = arrayOf<String>("select")
    val cid = null

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

        blinding.enterProductNametxt.setText(pname)
        blinding.enterProductPrice.setText(pprice)
        blinding.enterProductOffer.setText(poffer)
//        blinding.enterProductCategray.setText(pcat)
        blinding.enterProductDisc.setText(pdes)

        Glide.with(this).load(downloadimage).centerCrop().into(blinding.pimage)


        var firebaseDatabase = FirebaseDatabase.getInstance()
        var d = firebaseDatabase.reference

readDataCategry()
        blinding.uploadImg.setOnClickListener {

            gallaryupload()
        }

        blinding.insertBtn.setOnClickListener {

            updateImg()

        }
    }
    private fun readDataCategry() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        blinding.catSppiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(this@updateItem_acitivity, "${data[position]}", Toast.LENGTH_SHORT).show()

            }

        }



    }



    private fun updateImg() {

        var file = File(url.toString())
        var storage = Firebase.storage
        var key = intent.getStringExtra("n7")

        var storageReference = storage.reference.child(file.name)
        var UploadTask = storageReference.putFile(url!!)

        UploadTask.addOnSuccessListener { res ->

            storageReference.downloadUrl.addOnSuccessListener { uri ->

                if (uri != null) {

                    downloadUri = uri



                    var productModelData = ProductModelData(
                        blinding.enterProductNametxt.text.toString(),
                        blinding.enterProductPrice.text.toString(),
                        blinding.enterProductCategray.text.toString(),
                        blinding.enterProductOffer.text.toString(),
                        blinding.enterProductDisc.text.toString(),
                        downloadUri.toString(),
                        cid
                    )
                    var firebaseDatabase = FirebaseDatabase.getInstance()
                    var databaseReference = firebaseDatabase.reference

                    databaseReference.child("Product").child(key!!).setValue(productModelData)

                }
                var i = Intent(this, Home::class.java)
                startActivity(i)

            }
            Toast.makeText(this, "Sucees", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener { error ->

                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()

            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (100 == requestCode) {

            url = data?.data!!

            blinding.pimage.setImageURI(url)
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
