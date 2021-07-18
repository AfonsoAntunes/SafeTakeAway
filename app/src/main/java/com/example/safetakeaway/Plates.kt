package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Plates (var id: Long = -1, var name: String, var category: String, var price: Double, var restaurantId: Long) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(PlatesTable.NAME, name)
            put(PlatesTable.PRICE, price)
            put(PlatesTable.CATEGORY, category)
            put(PlatesTable.RESTAURANT_ID, restaurantId)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): Plates {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colName = cursor.getColumnIndex(PlatesTable.NAME)
            val colPrice = cursor.getColumnIndex(PlatesTable.PRICE)
            val colCategory = cursor.getColumnIndex(PlatesTable.CATEGORY)
            val colRestaurantId = cursor.getColumnIndex(PlatesTable.RESTAURANT_ID)

            val id = cursor.getLong(colId)
            val name = cursor.getString(colName)
            val category = cursor.getString(colCategory)
            val price = cursor.getDouble(colPrice)
            val restaurantId = cursor.getLong(colRestaurantId)

            return Plates(id, name, category, price, restaurantId)
        }
    }
}