package com.example.safetakeaway

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class OrderTable (db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun createTable() =
        db.execSQL(
            "CREATE TABLE $ORDER_TABLE (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $DATE DATE NOT NULL, $TOTAL_PRICE DOUBLE NOT NULL, $PAYMENT_METHOD TEXT NOT NULL,  $RESTAURANT_ID INTEGER NOT NULL, $PLATES_ID INTEGER NOT NULL, $USER_ID INTEGER NOT NULL, FOREIGN KEY ($RESTAURANT_ID) REFERENCES ${RestaurantTable.RESTAURANT_TABLE}, FOREIGN KEY ($PLATES_ID) REFERENCES ${PlatesTable.PLATES_TABLE}, FOREIGN KEY ($USER_ID) REFERENCES ${UserTable.USER_TABLE})"
        )

    fun insert(values: ContentValues): Long {
        return db.insert(ORDER_TABLE, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(ORDER_TABLE, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(ORDER_TABLE, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(ORDER_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val ORDER_TABLE = "Orders"
        const val TOTAL_PRICE = "Total_Price"
        const val DATE = "Date"
        const val PAYMENT_METHOD = "Payment_Method"
        const val RESTAURANT_ID = "Restaurant_ID"
        const val PLATES_ID = "Plate_ID"
        const val USER_ID = "User_ID"

        val ALL_FIELD = arrayOf(BaseColumns._ID, TOTAL_PRICE, DATE, PAYMENT_METHOD, RESTAURANT_ID, PLATES_ID, USER_ID)
    }
}