package com.example.safetakeaway

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestDatabase {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getDbTakeAwayOpenHelper() = DbTakeAwayOpenHelper(getAppContext())

    @Before
    fun deleteDb() {
        getAppContext().deleteDatabase(DbTakeAwayOpenHelper.DATABASE_NAME)
    }

    @Test
    fun getOpenDb() {
        val db = getDbTakeAwayOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }
}