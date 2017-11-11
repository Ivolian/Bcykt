package com.unicorn.bcykt.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.unicorn.bcykt.busLine.BusLineFra
import com.unicorn.bcykt.busStation.NearbyStationFra
import com.unicorn.bcykt.empty.EmptyFra

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        val titles = listOf("线路", "站点", "班车", "统计")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BusLineFra()
            1 -> NearbyStationFra()
            else -> EmptyFra()
        }
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

}
