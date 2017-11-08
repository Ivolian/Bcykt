package com.unicorn.bcykt

import android.content.Intent
import android.os.Bundle
import com.unicorn.bcykt.main.MainAct
import me.yokeyword.fragmentation.SupportActivity

class SplashAct : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        startActivity(Intent(this, MainAct::class.java))
    }

}
