package com.example.currency.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currency.R
import com.example.currency.ui.converter.ConverterActivity
import com.example.currency.ui.notes.NotesActivity
import com.example.currency.ui.splash.SplashActivity
import com.example.currency.util.constants.AppConstants
import com.example.currency.util.preferences.MySharedPreferences
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setup()
    }

    private fun setup() {
        notesBtnClicked()
        converterBtnClicked()
        signOutBtnClicked()
    }

    private fun notesBtnClicked() {
        notesBtn.setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }
    }

    private fun converterBtnClicked() {
        converterBtn.setOnClickListener {
            startActivity(Intent(this, ConverterActivity::class.java))
        }
    }

    private fun signOutBtnClicked() {
        signOutBtn.setOnClickListener {
            removeUserInfo()
        }
    }

    private fun removeUserInfo() {
        MySharedPreferences.removePreferences(AppConstants.LOGGED_IN_USER_NAME)
        MySharedPreferences.setBooleanValue(AppConstants.IS_USER_LOGGED_IN, false)
        resetApp()
    }

    private fun resetApp() {
        val i = Intent(this, SplashActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
        finish()
    }
}