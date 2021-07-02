package com.example.safetakeaway

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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

    private fun getCategoryTable(db: SQLiteDatabase) = CategoryTable(db)

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

    private fun getRestaurantDB(
        restaurantTable: RestaurantTable, id: Long): Restaurant {
        val cursor = restaurantTable.query(
            RestaurantTable.ALL_FIELD,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Restaurant.fromCursor(cursor)
    }

    private fun getPlatesDB(
        platesTable: PlatesTable, id: Long): Plates {
        val cursor = platesTable.query(
            PlatesTable.ALL_FIELD,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Plates.fromCursor(cursor)
    }

    private fun getOrderDB(
        orderTable: OrderTable, id: Long): Order {
        val cursor = orderTable.query(
            OrderTable.ALL_FIELD,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Order.fromCursor(cursor)
    }

    private fun getUserDB(
        userTable: UserTable, id: Long): User {
        val cursor = userTable.query(
            UserTable.ALL_FIELD,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return User.fromCursor(cursor)
    }

    private fun getCategoryDB(
        categoryTable: CategoryTable, id: Long): Category {
        val cursor = categoryTable.query(
            CategoryTable.ALL_FIELD,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Category.fromCursor(cursor)
    }

    private fun getCityDB(
        cityTable: CityTable, id: Long): City {
        val cursor = cityTable.query(
            CityTable.ALL_FIELD,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return City.fromCursor(cursor)
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

    @Test
    fun getCreateCategory() {
        val db = getDbTakeAwayOpenHelper().writableDatabase
        val categoryTable = getCategoryTable(db)

        val category = Category(type = "Italiana")
        category.id = insertCategory(categoryTable, category)

        val categoryId = getCategoryDB(categoryTable, category.id)

        assertEquals(category, categoryId)
        db.close()
    }
}