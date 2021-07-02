package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class City (var id: Long = -1, var city: String) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(CityTable.CITY, city)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): City {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colCity = cursor.getColumnIndex(CityTable.CITY)

            val id = cursor.getLong(colId)
            val city = cursor.getString(colCity)

            return City(id, city)
        }
    }
}