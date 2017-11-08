package com.unicorn.bcykt.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.unicorn.bcykt.empty.EmptyFra
import com.unicorn.bcykt.bus.BusStationFra

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
       return when(position){
            0 -> BusStationFra()
            else -> EmptyFra()

        }
    }

    override fun getCount() = titles.size

    private val titles = listOf("班车", "站点", "附近", "收藏", "实时")
    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}
