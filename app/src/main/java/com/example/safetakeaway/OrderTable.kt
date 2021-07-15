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
        /* val lastColumn = columns.size - 1

        // -1 indica que a coluna não foi pedida
        var posColNameRestaurant = -1
        var posColNamePlates = -1
        var posColNameUser = -1

        for (i in 0..lastColumn) {
            if (columns[i] == EXTERN_RESTAURANT_NAME) {
                posColNameRestaurant = i
            } else if (columns[i] == EXTERN_PLATES_NAME) {
                posColNamePlates = i
            } else if (columns[i] == EXTERN_USER_NAME) {
                posColNameUser = i
            }
        }

        if (posColNameRestaurant == -1 && posColNamePlates == -1 && posColNameUser == -1) {
            return db.query(ORDER_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var columnsFk = ""
        for (i in 0..lastColumn) {
            if (i > 0) columnsFk += ","

            columnsFk += if (i == posColNameRestaurant) {
                "${RestaurantTable.RESTAURANT_TABLE}.${RestaurantTable.NAME} AS $EXTERN_RESTAURANT_NAME"
            } else if (i == posColNamePlates) {
                "${PlatesTable.PLATES_TABLE}.${PlatesTable.NAME} AS $EXTERN_PLATES_NAME"
            } else if (i == posColNameUser) {
                "${UserTable.USER_TABLE}.${UserTable.NAME} AS $EXTERN_USER_NAME"
            } else {
                "${ORDER_TABLE}.${columns[i]}"
            }
        }

        val tables = "$ORDER_TABLE INNER JOIN ${RestaurantTable.RESTAURANT_TABLE} ON ${RestaurantTable.RESTAURANT_TABLE}.${BaseColumns._ID}=$RESTAURANT_ID INNER JOIN ${PlatesTable.PLATES_TABLE} ON ${PlatesTable.PLATES_TABLE}.${BaseColumns._ID}=$PLATES_ID INNER JOIN ${UserTable.USER_TABLE} ON ${UserTable.USER_TABLE}.${BaseColumns._ID}=$USER_ID"

        var sql = "SELECT $columnsFk FROM $tables"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        // TODO: ERROR -> ambiguous column name: _id (code 1 SQLITE_ERROR): , while compiling: SELECT Orders._id,Orders.Total_Price,Orders.Date,Orders.Payment_Method,Orders.Restaurant_ID,Orders.Plate_ID,Orders.User_ID,Restaurant.Name AS Restaurant_Name,Plates.Name AS Plates_Name,User.Name AS User_Name FROM Orders INNER JOIN Restaurant ON Restaurant._id=Restaurant_ID INNER JOIN Plates ON Plates._id=Plate_ID INNER JOIN User ON User._id=User_ID WHERE _id=?
        return db.rawQuery(sql, selectionArgs) */
    }

    companion object {
        const val ORDER_TABLE = "Orders"
        const val TOTAL_PRICE = "Total_Price"
        const val DATE = "Date"
        const val PAYMENT_METHOD = "Payment_Method"
        const val RESTAURANT_ID = "Restaurant_ID"
        const val PLATES_ID = "Plate_ID"
        const val USER_ID = "User_ID"
        // const val EXTERN_RESTAURANT_NAME = "Restaurant_Name"
        // const val EXTERN_PLATES_NAME = "Plates_Name"
        // const val EXTERN_USER_NAME = "User_Name"

        val ALL_FIELD = arrayOf(BaseColumns._ID, TOTAL_PRICE, DATE, PAYMENT_METHOD, RESTAURANT_ID, PLATES_ID, USER_ID /*, EXTERN_RESTAURANT_NAME, EXTERN_PLATES_NAME, EXTERN_USER_NAME */)
    }
}