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

    private fun insertRestaurant(restaurantTable: RestaurantTable, restaurant: Restaurant): Long {
        val id = restaurantTable.insert(restaurant.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insertPlates(platesTable: PlatesTable, plates: Plates): Long {
        val id = platesTable.insert(plates.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insertOrder(orderTable: OrderTable, order: Order): Long {
        val id = orderTable.insert(order.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insertUser(userTable: UserTable, user: User): Long {
        val id = userTable.insert(user.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insertCategory(categoryTable: CategoryTable, category: Category): Long {
        val id = categoryTable.insert(category.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insertCity(cityTable: CityTable, city: City): Long {
        val id = cityTable.insert(city.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

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