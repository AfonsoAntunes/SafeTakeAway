package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class CityTable(db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun createTable() =
        db.execSQL(
            "CREATE TABLE $CITY_TABLE (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CITY TEXT NOT NULL)"
        )

    fun insert(values: ContentValues): Long {
        return db.insert(CITY_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(CITY_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(CITY_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(CITY_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val CITY_TABLE = "City"
        const val CITY = "Name"

        val ALL_FIELD = arrayOf(BaseColumns._ID, CITY)
    }
}
