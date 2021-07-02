package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Restaurant (var id: Long = -1, var name: String, var categoryId: Long) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(RestaurantTable.NAME, name)
            put(RestaurantTable.CATEGORY_ID, categoryId)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): Restaurant {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colName = cursor.getColumnIndex(RestaurantTable.NAME)
            val colCategoryId = cursor.getColumnIndex(RestaurantTable.CATEGORY_ID)

            val id = cursor.getLong(colId)
            val name = cursor.getString(colName)
            val categoryId = cursor.getLong(colCategoryId)

            return Restaurant(id, name, categoryId)
        }
    }
}