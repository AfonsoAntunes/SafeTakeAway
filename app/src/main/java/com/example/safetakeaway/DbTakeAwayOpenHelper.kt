package com.example.safetakeaway

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbTakeAwayOpenHelper (context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            RestaurantTable(db).createTable()
            PlatesTable(db).createTable()
            OrderTable(db).createTable()
            UserTable(db).createTable()
            CategoryTable(db).createTable()
            CityTable(db).createTable()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val DATABASE_NAME = "takeAway.db"
        const val DATABASE_VERSION = 1
    }
}