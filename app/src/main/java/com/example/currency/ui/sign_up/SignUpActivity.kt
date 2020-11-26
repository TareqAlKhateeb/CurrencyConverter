package com.example.currency.ui.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.R
import com.example.currency.data.models.User
import com.example.currency.ui.home.HomeActivity
import com.example.currency.util.constants.AppConstants
import com.example.currency.util.extensions.showToast
import com.example.currency.util.extensions.validateField
import com.example.currency.util.preferences.MySharedPreferences
import com.example.currency.util.sqlite.UsersSQLiteManager
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        onSignUpClicked()

    }

    private fun onSignUpClicked() {
        signUpBtn.setOnClickListener {
            if (validateField(userNameET)) {
                if (validateField(passwordET))
                    checkIfUserExist()
                else
                    showToast(getString(R.string.invalid_password))
            } else
                showToast(getString(R.string.invalid_user_name))
        }
    }

    private fun checkIfUserExist() {
        val users = UsersSQLiteManager.getSQLiteData()
        if (!users.isNullOrEmpty())
            for (user in users) {
                if (user?.userName == userNameET.text.toString()){
                    showToast(getString(R.string.user_already_exist))
                    return
                }
            }
        saveUserInfoToDB()
    }

    private fun saveUserInfoToDB() {
        val user = User()
        user.userName = userNameET.text.toString()
        user.password = passwordET.text.toString()
        UsersSQLiteManager.insertIntoSQLiteDB(user)
        saveUserIdToSP()
    }

    private fun saveUserIdToSP() {
        MySharedPreferences.setStringValue(AppConstants.LOGGED_IN_USER_NAME, userNameET.text.toString())
        MySharedPreferences.setBooleanValue(AppConstants.IS_USER_LOGGED_IN, true)
        navigateToHomePage()
    }

    private fun navigateToHomePage() {
        val i = Intent(this, HomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
        finish()
    }

}