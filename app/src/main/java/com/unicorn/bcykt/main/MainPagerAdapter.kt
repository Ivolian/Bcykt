package com.unicorn.bcykt.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.unicorn.bcykt.bus.BusStationFra
import com.unicorn.bcykt.empty.EmptyFra

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        val titles = listOf("线路", "附近", "班车", "收藏")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> BusStationFra()
            else -> EmptyFra()
        }
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

}
