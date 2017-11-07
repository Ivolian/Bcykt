package com.unicorn.bcykt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_main.*

class MainAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        smartTab.setViewPager(viewPager)

    }

}