package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Order (var id: Long = -1, var totalPrice: Double, var date: Date, var paymentMethod: String, var restaurantId: Long, var platesId: Long, var userId: Long, /* var restaurantName: String? = null, var platesName: String? = null, var userName: String? = null */) {
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(OrderTable.TOTAL_PRICE, totalPrice)
            put(OrderTable.DATE, date.time)
            put(OrderTable.PAYMENT_METHOD, paymentMethod)
            put(OrderTable.RESTAURANT_ID, restaurantId)
            put(OrderTable.PLATES_ID, platesId)
            put(OrderTable.USER_ID, userId)
        }
        return values
    }

    companion object {
        fun fromCursor(cursor: Cursor): Order {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colTotalPrice = cursor.getColumnIndex(OrderTable.TOTAL_PRICE)
            val colDate = cursor.getColumnIndex(OrderTable.DATE)
            val colPaymentMethod = cursor.getColumnIndex(OrderTable.PAYMENT_METHOD)
            val colRestaurantId = cursor.getColumnIndex(OrderTable.RESTAURANT_ID)
            val colPlatesId = cursor.getColumnIndex(OrderTable.PLATES_ID)
            val colUserId = cursor.getColumnIndex(OrderTable.USER_ID)
            // val colRestaurantName = cursor.getColumnIndex(OrderTable.EXTERN_RESTAURANT_NAME)
            // val colPlatesName = cursor.getColumnIndex(OrderTable.EXTERN_PLATES_NAME)
            // val colUserName = cursor.getColumnIndex(OrderTable.EXTERN_USER_NAME)

            val id = cursor.getLong(colId)
            val totalPrice = cursor.getDouble(colTotalPrice)
            val date = cursor.getLong(colDate)
            val paymentMethod = cursor.getString(colPaymentMethod)
            val restaurantId = cursor.getLong(colRestaurantId)
            val platesId = cursor.getLong(colPlatesId)
            val userId = cursor.getLong(colUserId)
            // val restaurantName = if (colRestaurantName != -1) cursor.getString(colRestaurantName) else null
            // val platesName = if (colPlatesName != -1) cursor.getString(colPlatesName) else null
            // val userName = if (colUserName != -1) cursor.getString(colUserName) else null

            return Order(id, totalPrice, Date(date), paymentMethod, restaurantId, platesId, userId /*, restaurantName, platesName, userName */)
        }
    }
}