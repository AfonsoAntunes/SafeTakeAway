package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class RestaurantTable(db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun createTable() =
        db.execSQL(
            "CREATE TABLE $RESTAURANT_TABLE (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT NOT NULL, $CATEGORY_ID INTEGER NOT NULL, FOREIGN KEY ($CATEGORY_ID) REFERENCES ${CategoryTable.CATEGORY_TABLE})"
        )

    fun insert(values: ContentValues): Long {
        return db.insert(RESTAURANT_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(RESTAURANT_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(RESTAURANT_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(RESTAURANT_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val RESTAURANT_TABLE = "Restaurant"
        const val NAME = "Name"
        const val CATEGORY_ID = "Category_ID"

        val ALL_FIELD = arrayOf(BaseColumns._ID, NAME, CATEGORY_ID)
    }
}
