package com.unicorn.bcykt.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.unicorn.bcykt.busLine.LineFra
import com.unicorn.bcykt.nearby.NearbyFra
import com.unicorn.bcykt.other.EmptyFra

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        val titles = listOf("附近", "线路", "班车", "考勤")
    }

    override fun getItem(pos: Int) = when (pos) {
        0 -> NearbyFra()
        1 -> LineFra()
        else -> EmptyFra()
    }

    override fun getCount() = titles.size

    override fun getPageTitle(pos: Int) = titles[pos]

}
