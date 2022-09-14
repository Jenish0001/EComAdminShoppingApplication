package com.example.adminshoppingapplication.bothFragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class adpterlayout(fragment: login) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2

    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> adminFragment()
            1 -> userProfileFragment()
            else -> userProfileFragment()

        }
    }
}