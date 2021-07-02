package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Category (var id: Long = -1, var type: String) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(CategoryTable.TYPE, type)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): Category {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colType = cursor.getColumnIndex(CategoryTable.TYPE)

            val id = cursor.getLong(colId)
            val type = cursor.getString(colType)

            return Category(id, type)
        }
    }
}