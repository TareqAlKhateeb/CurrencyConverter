package com.example.currency.util.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.currency.data.models.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object MySharedPreferences {

    private var sharedPreference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun initWith(context: Context) {
        if (sharedPreference == null) sharedPreference = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    }

    fun setBooleanValue(key: String, value: Boolean) {
        editor = sharedPreference?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean? {
        return sharedPreference?.getBoolean(key, defaultValue)
    }

    fun setStringValue(key: String, value: String) {
        editor = sharedPreference?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun removePreferences(key: String) {
        editor = sharedPreference?.edit()
        editor?.remove(key)
        editor?.apply()
    }

    fun setArrayListValue(key: String, value: ArrayList<Note>) {
        editor = sharedPreference?.edit()
        val gSon = Gson()
        val json = gSon.toJson(value)
        editor?.putString(key, json)
        editor?.apply()
    }

    fun getArrayListValue(key: String): ArrayList<Note> {

        val notes: ArrayList<Note> = arrayListOf()

        val type: Type = object : TypeToken<List<Note>>() {}.type

        val gSon = Gson()

        val json = sharedPreference?.getString(key, "")

        if (!json.isNullOrEmpty()) {

            val arrPackageData: List<Note> = gSon.fromJson(json, type)

            for (data in arrPackageData) {

                notes.add(data)

            }
        }
        return notes

    }

}