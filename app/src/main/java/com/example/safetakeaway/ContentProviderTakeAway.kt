package com.example.safetakeaway

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderTakeAway : ContentProvider() {
    private var dbTakeAwayOpenHelper : DbTakeAwayOpenHelper? = null
    override fun onCreate(): Boolean {
        dbTakeAwayOpenHelper = DbTakeAwayOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbTakeAwayOpenHelper!!.readableDatabase

        return when (getUriMatcher().match(uri)) {
            ORDER_URI -> OrderTable(db).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            SPECIFIC_ORDER_URI -> OrderTable(db).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            USER_URI -> UserTable(db).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            SPECIFIC_USER_URI -> UserTable(db).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            PLATES_URI -> PlatesTable(db).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            SPECIFIC_PLATES_URI -> PlatesTable(db).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            RESTAURANT_URI -> RestaurantTable(db).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            SPECIFIC_RESTAURANT_URI -> RestaurantTable(db).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)) {
            ORDER_URI -> "$MULTIPLE_ITEMS/$ORDER"
            SPECIFIC_ORDER_URI -> "$SINGLE_ITEM/$ORDER"
            USER_URI -> "$MULTIPLE_ITEMS/$USER"
            SPECIFIC_USER_URI -> "$SINGLE_ITEM/$USER"
            PLATES_URI -> "$MULTIPLE_ITEMS/$PLATES"
            SPECIFIC_PLATES_URI -> "$SINGLE_ITEM/$PLATES"
            RESTAURANT_URI -> "$MULTIPLE_ITEMS/$RESTAURANT"
            SPECIFIC_RESTAURANT_URI -> "$SINGLE_ITEM/$RESTAURANT"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbTakeAwayOpenHelper!!.writableDatabase

        val id = when (getUriMatcher().match(uri)) {
            ORDER_URI -> OrderTable(db).insert(values!!)
            USER_URI -> UserTable(db).insert(values!!)
            PLATES_URI -> PlatesTable(db).insert(values!!)
            RESTAURANT_URI -> RestaurantTable(db).insert(values!!)
            else -> -1L
        }

        if (id == -1L)
            return null

        return Uri.withAppendedPath(uri, toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbTakeAwayOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)) {

            SPECIFIC_ORDER_URI -> OrderTable(db).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            SPECIFIC_USER_URI -> UserTable(db).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            SPECIFIC_PLATES_URI -> PlatesTable(db).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            SPECIFIC_RESTAURANT_URI -> RestaurantTable(db).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val db = dbTakeAwayOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)) {
            SPECIFIC_ORDER_URI -> OrderTable(db).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            SPECIFIC_USER_URI -> UserTable(db).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            SPECIFIC_PLATES_URI -> PlatesTable(db).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            SPECIFIC_RESTAURANT_URI -> RestaurantTable(db).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            else -> 0
        }
    }

    companion object {
        private const val AUTHORITY = "com.example.safetakeaway"

        private const val ORDER = "order"
        private const val USER = "user"
        private const val PLATES = "plates"
        private const val RESTAURANT = "restaurant"

        private const val ORDER_URI = 100
        private const val SPECIFIC_ORDER_URI = 101
        private const val USER_URI = 200
        private const val SPECIFIC_USER_URI = 201
        private const val PLATES_URI = 400
        private const val SPECIFIC_PLATES_URI = 401
        private const val RESTAURANT_URI = 50
        private const val SPECIFIC_RESTAURANT_URI = 501

        private const val MULTIPLE_ITEMS = "vnd.android.cursor.dir"
        private const val SINGLE_ITEM = "vnd.android.cursor.item"

        private val BASE_ADDRESS = Uri.parse("content://$AUTHORITY")

        public val ORDER_ADDRESS = Uri.withAppendedPath(BASE_ADDRESS, ORDER)
        public val USER_ADDRESS = Uri.withAppendedPath(BASE_ADDRESS, USER)
        public val PLATES_ADDRESS = Uri.withAppendedPath(BASE_ADDRESS, PLATES)
        public val RESTAURANT_ADDRESS = Uri.withAppendedPath(BASE_ADDRESS, RESTAURANT)

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, ORDER, ORDER_URI)
            uriMatcher.addURI(AUTHORITY, "$ORDER/#", SPECIFIC_ORDER_URI)
            uriMatcher.addURI(AUTHORITY, USER, USER_URI)
            uriMatcher.addURI(AUTHORITY, "$USER/#", SPECIFIC_USER_URI)
            uriMatcher.addURI(AUTHORITY, PLATES, PLATES_URI)
            uriMatcher.addURI(AUTHORITY, "$PLATES/#", SPECIFIC_PLATES_URI)
            uriMatcher.addURI(AUTHORITY, RESTAURANT, RESTAURANT_URI)
            uriMatcher.addURI(AUTHORITY, "$RESTAURANT/#", SPECIFIC_RESTAURANT_URI)

            return uriMatcher
        }
    }
}