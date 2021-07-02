package com.example.safetakeaway

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

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
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val AUTHORITY = "com.example.covidfinalproject"

        private const val ORDER = "order"
        private const val USER = "user"
        private const val CITY = "city"
        private const val PLATES = "plates"
        private const val RESTAURANT = "restaurant"
        private const val CATEGORY = "category"

        private const val ORDER_URI = 100;
        private const val SPECIFIC_ORDER_URI = 101;
        private const val USER_URI = 200;
        private const val SPECIFIC_USER_URI = 201;
        private const val CITY_URI = 300;
        private const val SPECIFIC_CITY_URI = 301;
        private const val PLATES_URI = 400;
        private const val SPECIFIC_PLATES_URI = 401;
        private const val RESTAURANT_URI = 500;
        private const val SPECIFIC_RESTAURANT_URI = 501;
        private const val CATEGORY_URI = 600;
        private const val SPECIFIC_CATEGORY_URI = 601;

        private val BASE_ADDRESS = Uri.parse("content://$AUTHORITY")

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, ORDER, ORDER_URI)
            uriMatcher.addURI(AUTHORITY, "$ORDER/#", SPECIFIC_ORDER_URI)
            uriMatcher.addURI(AUTHORITY, USER, USER_URI)
            uriMatcher.addURI(AUTHORITY, "$USER/#", SPECIFIC_USER_URI)
            uriMatcher.addURI(AUTHORITY, CITY, CITY_URI)
            uriMatcher.addURI(AUTHORITY, "$CITY/#", SPECIFIC_CITY_URI)
            uriMatcher.addURI(AUTHORITY, PLATES, PLATES_URI)
            uriMatcher.addURI(AUTHORITY, "$PLATES/#", SPECIFIC_PLATES_URI)
            uriMatcher.addURI(AUTHORITY, RESTAURANT, RESTAURANT_URI)
            uriMatcher.addURI(AUTHORITY, "$RESTAURANT/#", SPECIFIC_RESTAURANT_URI)
            uriMatcher.addURI(AUTHORITY, CATEGORY, CATEGORY_URI)
            uriMatcher.addURI(AUTHORITY, "$CATEGORY/#", SPECIFIC_CATEGORY_URI)

            return uriMatcher
        }
    }
}