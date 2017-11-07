package com.unicorn.bcykt

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        startActivity(Intent(this, MainAct::class.java))
    }

}
