package com.unicorn.bcykt

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return SimpleFra()
    }

    override fun getCount()= titles.size

    private val titles = listOf("班车","附近","常用")
    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}
