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

    /*
        val lastColumn = columns.size - 1

        var posColNameUser = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..lastColumn) {
            if (columns[i] == EXTERN_USER_NAME) {
                posColNameUser = i
                break
            }
        }

        if (posColNameUser == -1) {
            return db.query(ORDER_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var columnsUser = ""
        for (i in 0..lastColumn) {
            if (i > 0) columnsUser += ","

            columnsUser += if (i == posColNameUser) {
                "${UserTable.USER_TABLE}.${UserTable.NAME} AS $EXTERN_USER_NAME"
            } else {
                "${ORDER_TABLE}.${columns[i]}"
            }
        }

        val tables = "$ORDER_TABLE INNER JOIN ${UserTable.USER_TABLE} ON ${UserTable.USER_TABLE}.${BaseColumns._ID}=$USER_ID"

        var sql = "SELECT $columnsUser FROM $tables"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        // TODO: ERROR -> ambiguous column name: _id (code 1 SQLITE_ERROR): , while compiling: SELECT Orders._id,Orders.Total_Price,Orders.Date,Orders.Payment_Method,Orders.Restaurant_ID,Orders.Plate_ID,Orders.User_ID,User.Name AS User_Name FROM Orders INNER JOIN User ON User._id=User_ID WHERE _id=?
        return db.rawQuery(sql, selectionArgs)
        */
    }

    companion object {
        const val ORDER_TABLE = "Orders"
        const val TOTAL_PRICE = "Total_Price"
        const val DATE = "Date"
        const val PAYMENT_METHOD = "Payment_Method"
        const val RESTAURANT_ID = "Restaurant_ID"
        const val PLATES_ID = "Plate_ID"
        const val USER_ID = "User_ID"
        // const val EXTERN_USER_NAME = "User_Name"

        val ALL_FIELD = arrayOf(BaseColumns._ID, TOTAL_PRICE, DATE, PAYMENT_METHOD, RESTAURANT_ID, PLATES_ID, USER_ID, /* EXTERN_USER_NAME */)
    }
}