package com.example.currency.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.R
import com.example.currency.ui.home.HomeActivity
import com.example.currency.ui.sign_up.SignUpActivity
import com.example.currency.util.constants.AppConstants.IS_USER_LOGGED_IN
import com.example.currency.util.constants.AppConstants.LOGGED_IN_USER_NAME
import com.example.currency.util.extensions.showToast
import com.example.currency.util.extensions.validateField
import com.example.currency.util.preferences.MySharedPreferences
import com.example.currency.util.sqlite.UsersSQLiteManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.passwordET
import kotlinx.android.synthetic.main.activity_login.signUpBtn
import kotlinx.android.synthetic.main.activity_login.userNameET

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()

    }

    private fun setup() {
        onLoginClicked()
        onSignUpClicked()
    }

    private fun onLoginClicked() {
        loginBtn.setOnClickListener {
            if (validateField(userNameET)) {
                if (validateField(passwordET))
                    checkIfUserExist()
                else
                    showToast(getString(R.string.invalid_password))
            } else
                showToast(getString(R.string.invalid_user_name))
        }
    }

    private fun onSignUpClicked() {
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun checkIfUserExist() {
        val users = UsersSQLiteManager.getSQLiteData()
        if (!users.isNullOrEmpty())
            for (user in users) {
                if (user?.userName == userNameET.text.toString() && user.password == passwordET.text.toString()){
                    saveUserIdToSP()
                    return
                }
            }
        showToast(getString(R.string.wrong_user_or_password))
    }

    private fun saveUserIdToSP() {
        MySharedPreferences.setStringValue(LOGGED_IN_USER_NAME, userNameET.text.toString())
        MySharedPreferences.setBooleanValue(IS_USER_LOGGED_IN, true)
        navigateToHomePage()
    }

    private fun navigateToHomePage() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}