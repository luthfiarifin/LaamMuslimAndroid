package com.laam.laammuslim.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.laam.laammuslim.R
import com.laam.laammuslim.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            Intent(this@SplashActivity, MainActivity::class.java).let {
                startActivity(it)
                finish()
            }
        }, 1000)
    }
}
