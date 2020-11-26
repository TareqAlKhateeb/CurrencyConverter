package com.example.currency.util.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersDB(con: Context): SQLiteOpenHelper(con,"Currency.db",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table users(user text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}