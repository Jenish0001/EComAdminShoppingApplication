package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminshoppingapplication.AdminShopping.Model.ModelReadData
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.Activity.wishList
import com.example.adminshoppingapplication.UserShopping.adpter.userAdpter
import com.example.adminshoppingapplication.databinding.FragmentHomeFrgmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class homeFrgment() : Fragment() {

    var list1 = arrayListOf<CarouselItem>()
    var list = arrayListOf<ModelReadData>()
    lateinit var bliding: FragmentHomeFrgmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        bliding = FragmentHomeFrgmentBinding.inflate(layoutInflater)

        readdata()

        usersetName()
        imageSlider()

        wichlist()

        return bliding.root
    }

    private fun wichlist() {

        bliding.wishlistBtn.setOnClickListener {

            var i=Intent(activity,wishList::class.java)
            activity?.startActivity(i)
        }
    }

    private fun usersetName() {

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var u = user?.photoUrl
        var uname = user?.displayName

        Glide.with(requireActivity()).load(u).into(bliding.userImg)
        bliding.userTxt.text = uname
    }


    private fun setuprv(list: ArrayList<ModelReadData>) {

        var useradpter = userAdpter(activity, list)
        var layoutManager = GridLayoutManager(activity, 2)
        bliding.recycler.adapter = useradpter
        bliding.recycler.layoutManager = layoutManager

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

    fun imageSlider() {

        list1.clear()

        list1.add(
            CarouselItem(
                imageUrl = "https://www.designhill.com/design-blog/wp-content/uploads/2018/10/Marley-Tshirts.jpg",
                caption = "T-Shirts"
            )
        )
        list1.add(
            CarouselItem(
                imageUrl = "https://i.pinimg.com/originals/66/23/c7/6623c7a1ecffd8971c3baf9dcb148ae3.jpg",
                caption = "All-Phone"
            )
        )
        list1.add(
            CarouselItem(
                imageUrl = "https://www.91-cdn.com/hub/wp-content/uploads/2021/03/Laptop-under-50000.jpg",
                caption = "Leptop"
            )
        )
        list1.add(
            CarouselItem(
                imageUrl = "https://www.wareable.com/media/imager/202107/35589-original.jpg",
                caption = "Watch"
            )
        )

        list1.add(
            CarouselItem(
                imageUrl = "https://www.hp.com/us-en/shop/app/assets/images/uploads/prod/How%20to%20Clean%20a%20Computer%20Keyboard1646149371308147.jpg",
                caption = "Keyboard"
            )
        )


        bliding.carouselHomeFragment.setData(list1)
    }

}