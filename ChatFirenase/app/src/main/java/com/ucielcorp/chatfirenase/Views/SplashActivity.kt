package com.ucielcorp.chatfirenase.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ucielcorp.chatfirenase.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Moviendonos a otroa activity
        val splasScreemTimeOut = 4000
        val homeIntent = Intent(this, LoginActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splasScreemTimeOut.toLong())
    }
}