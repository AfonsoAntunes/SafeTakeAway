package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Plates (var id: Long = -1, var name: String, var price: Double, var categoryId: Long, var restaurantId: Long) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(PlatesTable.NAME, name)
            put(PlatesTable.PRICE, price)
            put(PlatesTable.CATEGORY_ID, categoryId)
            put(PlatesTable.RESTAURANT_ID, restaurantId)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): Plates {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colName = cursor.getColumnIndex(PlatesTable.NAME)
            val colPrice = cursor.getColumnIndex(PlatesTable.PRICE)
            val colCategoryId = cursor.getColumnIndex(PlatesTable.CATEGORY_ID)
            val colRestaurantId = cursor.getColumnIndex(PlatesTable.RESTAURANT_ID)

            val id = cursor.getLong(colId)
            val name = cursor.getString(colName)

            val price = cursor.getDouble(colPrice)
            val categoryId = cursor.getLong(colCategoryId)
            val restaurantId = cursor.getLong(colRestaurantId)

            return Plates(id, name, price, categoryId, restaurantId)
        }
    }
}