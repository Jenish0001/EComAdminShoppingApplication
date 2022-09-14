package com.example.adminshoppingapplication.AdminShopping.AdminScreen

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.adminshoppingapplication.AdminShopping.Adpter.Spinner_Adpter
import com.example.adminshoppingapplication.AdminShopping.Home
import com.example.adminshoppingapplication.AdminShopping.Model.ProductModelData
import com.example.adminshoppingapplication.AdminShopping.ModelData
import com.example.adminshoppingapplication.R
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
    private val CHANNEL_ID = "heads_up_alerts"
    private val notificationId = 101
    var url: Uri? = null
    lateinit var blinding: ActivityMainBinding
    var list1 = arrayListOf<ModelData>()
    var stringList = arrayListOf<String>()
    var data = arrayOf<String>("select")
    var downloadUri: Uri? = null
    lateinit var catename: String
    var cid: Int? = null

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
            creatnotification()
            sendnotification()


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
        var spinnerAdpter = Spinner_Adpter(this, data)
        blinding.catSppiner.adapter = spinnerAdpter
        spinnerAdpter.notifyDataSetChanged()
    }


    private fun creatnotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Notification Title"
            val descriptionText = "Notification Description Text "
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val chennel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(chennel)

        }
    }

    fun sendnotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        var bitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.ic_launcher_foreground
        )
        var bitmapLargeIcon =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)
        var name = blinding.enterProductNameTxt.text.toString()
        var price = blinding.enterProductPriceTxt.text.toString()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(name)
            .setContentText(price)
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }

    }


}
