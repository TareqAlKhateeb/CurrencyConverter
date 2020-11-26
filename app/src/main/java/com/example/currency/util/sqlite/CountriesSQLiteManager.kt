package com.example.currency.util.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.currency.data.models.Countries
import com.example.currency.data.models.User
import com.google.gson.Gson

object CountriesSQLiteManager {

    private var obj: CountriesDB? = null
    private lateinit var db: SQLiteDatabase
    private var gSon : Gson? = null

    fun initWith(context: Context) {
        gSon = Gson()
        obj = CountriesDB(context)
        db = obj!!.writableDatabase
    }

    fun getSQLiteData(): Countries? {
        val cur = db.rawQuery("select * from countries", null)
        if (cur.count > 0) {
            while (cur.moveToNext()) {
                val countries = cur.getString(cur.getColumnIndex("country"))
                return gSon?.fromJson(countries, Countries::class.java)
            }
            cur.close()
        } else {
            return null
        }
        return null
    }

    fun insertIntoSQLiteDB(it: Countries) {
        db.execSQL("delete from countries")
        val countries: String? =
            gSon?.toJson(it, Countries::class.java)
        db.execSQL("insert into countries values(?)", arrayOf(countries))
    }

}