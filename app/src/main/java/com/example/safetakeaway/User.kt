package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class User (var id: Long = -1, var name: String, var gender: String, var address: String, var email: String, var phoneNumber: String, var cityId: Long) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(UserTable.NAME, name)
            put(UserTable.GENDER, gender)
            put(UserTable.ADDRESS, address)
            put(UserTable.EMAIL, email)
            put(UserTable.PHONE_NUMBER, phoneNumber)
            put(UserTable.CITY_ID, cityId)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): User {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colName = cursor.getColumnIndex(UserTable.NAME)
            val colGender = cursor.getColumnIndex(UserTable.GENDER)
            val colAddress = cursor.getColumnIndex(UserTable.ADDRESS)
            val colEmail = cursor.getColumnIndex(UserTable.EMAIL)
            val colPhoneNumber = cursor.getColumnIndex(UserTable.PHONE_NUMBER)
            val colCityId = cursor.getColumnIndex(UserTable.CITY_ID)

            val id = cursor.getLong(colId)
            val name = cursor.getString(colName)
            val gender = cursor.getString(colGender)
            val address = cursor.getString(colAddress)
            val email = cursor.getString(colEmail)
            val phoneNumber = cursor.getString(colPhoneNumber)
            val cityId = cursor.getLong(colCityId)


            return User (id, name, gender, address, email, phoneNumber, cityId)
        }
    }
}