package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class PlatesTable (db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun createTable() =
        db.execSQL(
            "CREATE TABLE $PLATES_TABLE (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT NOT NULL, $PRICE DOUBLE NOT NULL, $RESTAURANT_ID INTEGER NOT NULL, FOREIGN KEY ($RESTAURANT_ID) REFERENCES ${RestaurantTable.RESTAURANT_TABLE})"
        )

    fun insert(values: ContentValues): Long {
        return db.insert(PLATES_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(PLATES_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(PLATES_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(PLATES_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val PLATES_TABLE = "Plates"
        const val NAME = "Name"
        const val PRICE = "Price"
        const val RESTAURANT_ID = "Restaurant_ID"

        val ALL_FIELD = arrayOf(BaseColumns._ID, NAME, PRICE, RESTAURANT_ID)
    }
}