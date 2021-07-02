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

        private val BASE_ADDRESS = Uri.parse("content://$AUTHORITY")

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, ORDER, 100)
            uriMatcher.addURI(AUTHORITY, "$ORDER/#", 101)
            uriMatcher.addURI(AUTHORITY, USER, 200)
            uriMatcher.addURI(AUTHORITY, "$USER/#", 201)
            uriMatcher.addURI(AUTHORITY, CITY, 300)
            uriMatcher.addURI(AUTHORITY, "$CITY/#", 301)
            uriMatcher.addURI(AUTHORITY, PLATES, 400)
            uriMatcher.addURI(AUTHORITY, "$PLATES/#", 401)
            uriMatcher.addURI(AUTHORITY, RESTAURANT, 500)
            uriMatcher.addURI(AUTHORITY, "$RESTAURANT/#", 501)
            uriMatcher.addURI(AUTHORITY, CATEGORY, 600)
            uriMatcher.addURI(AUTHORITY, "$CATEGORY/#", 601)

            return uriMatcher
        }
    }
}