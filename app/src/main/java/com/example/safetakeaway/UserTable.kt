package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class UserTable (db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun createTable() =
        db.execSQL(
            "CREATE TABLE $USER_TABLE (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT NOT NULL, $GENDER TEXT NOT NULL, $ADDRESS TEXT NOT NULL, $CITY TEXT NOT NULL, $EMAIL TEXT NOT NULL, $PHONE_NUMBER TEXT NOT NULL)"
        )

    fun insert(values: ContentValues): Long {
        return db.insert(USER_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(USER_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(USER_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(USER_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val USER_TABLE = "User"
        const val NAME = "Name"
        const val GENDER = "Gender"
        const val ADDRESS = "Address"
        const val CITY = "City"
        const val EMAIL = "Email"
        const val PHONE_NUMBER = "Phone_Number"

        val ALL_FIELD = arrayOf(BaseColumns._ID, NAME, GENDER, ADDRESS, CITY, EMAIL, PHONE_NUMBER)
    }
}
