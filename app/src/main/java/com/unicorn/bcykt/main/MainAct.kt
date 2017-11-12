package com.unicorn.bcykt.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.unicorn.bcykt.R
import com.unicorn.bcykt.main.MainPagerAdapter.Companion.titles
import kotlinx.android.synthetic.main.act_main.*


class MainAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        viewPager.offscreenPageLimit = titles.size - 1
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        tab.setViewPager(viewPager)
    }

}