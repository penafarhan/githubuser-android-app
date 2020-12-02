package com.codeiva.githubuserapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.codeiva.githubuserapp.R
import com.codeiva.githubuserapp.ui.home.HomeActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 1000)
    }
}