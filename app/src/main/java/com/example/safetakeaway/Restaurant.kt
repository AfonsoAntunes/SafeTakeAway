package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Restaurant (var id: Long = -1, var name: String, var category: String) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(RestaurantTable.NAME, name)
            put(RestaurantTable.CATEGORY, category)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): Restaurant {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colName = cursor.getColumnIndex(RestaurantTable.NAME)
            val colCategory = cursor.getColumnIndex(RestaurantTable.CATEGORY)

            val id = cursor.getLong(colId)
            val name = cursor.getString(colName)
            val category = cursor.getString(colCategory)

            return Restaurant(id, name, category)
        }
    }
}