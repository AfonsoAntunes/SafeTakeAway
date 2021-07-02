package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class CategoryTable(db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun createTable() =
        db.execSQL(
            "CREATE TABLE $CATEGORY_TABLE (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $TYPE TEXT NOT NULL)"
        )

    fun insert(values: ContentValues): Long {
        return db.insert(CATEGORY_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(CATEGORY_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(CATEGORY_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(CATEGORY_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val CATEGORY_TABLE = "Category"
        const val TYPE = "Name"

        val ALL_FIELD = arrayOf(BaseColumns._ID, TYPE)
    }
}
