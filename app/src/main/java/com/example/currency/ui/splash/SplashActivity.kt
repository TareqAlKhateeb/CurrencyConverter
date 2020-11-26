package com.example.currency.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.R
import com.example.currency.data.network.IApiCaller.callApiGetCountries
import com.example.currency.ui.home.HomeActivity
import com.example.currency.ui.login.LoginActivity
import com.example.currency.util.constants.AppConstants
import com.example.currency.util.preferences.MySharedPreferences

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        callApiGetCountries()

        Handler(Looper.getMainLooper()).postDelayed({
            checkIfUserLoggedIn()
        }, 1000)

    }

    private fun checkIfUserLoggedIn() {
        if (MySharedPreferences.getBooleanValue(AppConstants.IS_USER_LOGGED_IN,false) == true){
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}