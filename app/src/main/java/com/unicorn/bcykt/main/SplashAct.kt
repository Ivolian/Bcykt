package com.unicorn.bcykt.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.unicorn.bcykt.R

class SplashAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        startActivity(Intent(this, MainAct::class.java))
    }

}
