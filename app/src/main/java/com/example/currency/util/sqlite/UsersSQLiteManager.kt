package com.example.currency.util.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.currency.data.models.User
import com.google.gson.Gson

object UsersSQLiteManager {

    private var obj: UsersDB? = null
    private lateinit var db: SQLiteDatabase
    private var gSon : Gson? = null

    fun initWith(context: Context) {
        gSon = Gson()
        obj = UsersDB(context)
        db = obj!!.writableDatabase
    }

    fun getSQLiteData(): ArrayList<User?>?  {
        val cur = db.rawQuery("select * from users", null)
        return if (cur.count > 0) {
            val usersArray: ArrayList<User?> = ArrayList()
            while (cur.moveToNext()) {
                val userString = cur.getString(cur.getColumnIndex("user"))
                val user = gSon?.fromJson(userString, User::class.java)
                usersArray.add(user)
            }
            cur.close()
            usersArray
        } else {
            null
        }
    }

    fun insertIntoSQLiteDB(it: User) {
        val user: String? =
            gSon?.toJson(it, User::class.java)
        db.execSQL("insert into users values(?)", arrayOf(user))
    }

}