package com.example.adminshoppingapplication.UserShopping.fragmentActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshoppingapplication.R
import com.example.adminshoppingapplication.UserShopping.adpter.categryRvview
import com.example.adminshoppingapplication.UserShopping.adpter.showRvview
import com.example.adminshoppingapplication.UserShopping.modelClass.ModelReadcatagry
import com.example.adminshoppingapplication.databinding.FragmentCategryBinding
import com.google.firebase.database.*
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class categryFragment() : Fragment() {

    var list1 = arrayListOf<CarouselItem>()
    var name = arrayOf("Watch", "Phone", "tablet", "Earphone", "suit", "sunglasses")
    var imge = arrayOf(
        R.drawable.cwatch,
        R.drawable.phonef,
        R.drawable.tabletf,
        R.drawable.earphone,
        R.drawable.suit,
        R.drawable.sunglasses
    )

    var list = arrayListOf<ModelReadcatagry>()

    companion object {
        lateinit var sbinding: FragmentCategryBinding
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sbinding = FragmentCategryBinding.inflate(layoutInflater)

        imageSlider()
        categryRecyclerView()

        return sbinding.root
    }


    private fun categryRecyclerView() {

        var adpter = categryRvview(activity, name, imge)
        var layouet = LinearLayoutManager(activity)
        layouet.orientation = LinearLayoutManager.HORIZONTAL
        sbinding.categryRvview.adapter = adpter
        sbinding.categryRvview.layoutManager = layouet

    }

    fun imageSlider() {
        list1.clear()
        list1.add(
            CarouselItem(
                imageUrl = "https://images-eu.ssl-images-amazon.com/images/W/WEBP_402378-T1/images/G/31/Deals/Jupiter22/KSD/KD_PC_UNREC.jpg",
//                caption = "T-Shirts"
            )
        )
        list1.add(
            CarouselItem(
                imageUrl = "https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/A1ABoFGfzQL._SX3000_.jpg",
//                caption = "All-Phone"
            )
        )
        list1.add(
            CarouselItem(
                imageUrl = "https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/81uJIpEvwfL._SX3000_.jpg",
//                caption = "Leptop"
            )
        )
//        list1.add(
//            CarouselItem(
//                imageUrl = "https://www.wareable.com/media/imager/202107/35589-original.jpg",
//                caption = "Watch"
//            )
//        )
//
//        list1.add(
//            CarouselItem(
//                imageUrl = "https://www.hp.com/us-en/shop/app/assets/images/uploads/prod/How%20to%20Clean%20a%20Computer%20Keyboard1646149371308147.jpg",
//                caption = "Keyboard"
//            )
//        )


        sbinding.carousel.setData(list1)
    }


}