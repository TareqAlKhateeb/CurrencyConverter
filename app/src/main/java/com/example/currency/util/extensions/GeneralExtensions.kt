package com.example.currency.util.extensions

import android.widget.EditText
import android.widget.Toast
import com.example.currency.data.models.Countries
import com.example.currency.ui.base.App
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun validateField(editText: EditText): Boolean {

    if (!editText.text.isNullOrBlank())
        return true
    return false

}

fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    if (App.applicationContext() != null)
        Toast.makeText(App.applicationContext(), message, duration).show()
}

fun parseResponse(response: String, java: Any): Any? {
    return if (isJSONValid(response)) {
        val jsonObject = JSONObject(response)
        val jsonString = jsonObject.toString()
        Gson().fromJson(jsonString, java::class.java)
    } else
        false
}

private fun isJSONValid(test: String?): Boolean {
    try {
        JSONObject(test.toString())
    } catch (ex: JSONException) {
        try {
            JSONArray(test.toString())
        } catch (ex1: JSONException) {
            return false
        }
    }
    return true
}

fun getCountriesAsStringArray(countries: Countries?): ArrayList<String>? {
    val arrayOfCountries: ArrayList<String> = ArrayList()
    if (countries != null) {
        for (country in countries.results!!){
            arrayOfCountries.add(country.value.name.toString())
        }
        return arrayOfCountries
    }
    return null
}