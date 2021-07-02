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
    private fun getRestaurantTable(db: SQLiteDatabase) = RestaurantTable(db)
    private fun getPlatesTable(db: SQLiteDatabase) = PlatesTable(db)
    private fun getCityTable(db: SQLiteDatabase) = CityTable(db)

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

    @Test
    fun getReadCategory() {
        val db = getDbTakeAwayOpenHelper().writableDatabase
        val categoryTable = getCategoryTable(db)

        val category = Category(type = "Italiana")
        category.id = insertCategory(categoryTable, category)

        val categoryId = getCategoryDB(categoryTable, category.id)

        assertEquals(category, categoryId)
        db.close()
    }

    @Test
    fun getUpdateCategory() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Portuguesa")
        category.id = insertCategory(categoryTable, category)

        category.type = "Brasileira"

        val changedData = categoryTable.update(category.toContentValues(), "${BaseColumns._ID}=?", arrayOf(category.id.toString()))
        assertEquals(1, changedData)

        val categoryId = getCategoryDB(categoryTable, category.id)

        assertEquals(category, categoryId)
        db.close()
    }

    @Test
    fun getDeleteCategory() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Test")
        category.id = insertCategory(categoryTable, category)

        val deletedData = categoryTable.delete("${BaseColumns._ID}=?", arrayOf(category.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreateRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Italiana")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Tiago's Pizza", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val restaurantId = getRestaurantDB(restaurantTable, restaurant.id)

        assertEquals(restaurant, restaurantId)
        db.close()
    }

    @Test
    fun getReadRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Italiana")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Central 2", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val restaurantId = getRestaurantDB(restaurantTable, restaurant.id)

        assertEquals(restaurant, restaurantId)
        db.close()
    }

    @Test
    fun getUpdateRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Portuguesa")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Sardinha", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        restaurant.name = "Videira"

        val changedData = restaurantTable.update(restaurant.toContentValues(), "${BaseColumns._ID}=?", arrayOf(restaurant.id.toString()))
        assertEquals(1, changedData)

        val restaurantId = getRestaurantDB(restaurantTable, restaurant.id)

        assertEquals(restaurant, restaurantId)
        db.close()
    }

    @Test
    fun getDeleteRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Test")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Test", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val deletedData = restaurantTable.delete("${BaseColumns._ID}=?", arrayOf(restaurant.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreatePlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Italiana")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Telepizza", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Bolonhesa", price = 7.99, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val platesId = getPlatesDB(platesTable, plate.id)

        assertEquals(plate, platesId)
        db.close()
    }

    @Test
    fun getReadPlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Castelhana")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "O Ferrinho", categoryId = category.id,)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Cabrito Assado", price = 7.99, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val platesId = getPlatesDB(platesTable, plate.id)

        assertEquals(plate, platesId)
        db.close()
    }

    @Test
    fun getUpdatePlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Portuguesa")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Sardinha", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Sardinha Assada", price = 9.99, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        plate.price = 7.49

        val changedData = platesTable.update(plate.toContentValues(), "${BaseColumns._ID}=?", arrayOf(plate.id.toString()))
        assertEquals(1, changedData)

        db.close()
    }

    @Test
    fun getDeletePlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Test")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Test", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Test0",  price = 5.00, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val deletedData = platesTable.delete("${BaseColumns._ID}=?", arrayOf(plate.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreateCity() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Guarda")
        city.id = insertCity(cityTable, city)

        val cityId = getCityDB(cityTable, city.id)

        assertEquals(city, cityId)
        db.close()
    }

    @Test
    fun getReadCity() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Guarda")
        city.id = insertCity(cityTable, city)

        val cityId = getCityDB(cityTable, city.id)

        assertEquals(city, cityId)
        db.close()
    }

    @Test
    fun getUpdateCity() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Guarda")
        city.id = insertCity(cityTable, city)

        city.city = "Coimbra"

        val changedData = cityTable.update(city.toContentValues(), "${BaseColumns._ID}=?", arrayOf(city.id.toString()))
        assertEquals(1, changedData)

        val cityId = getCityDB(cityTable, city.id)

        assertEquals(city, cityId)
        db.close()
    }
}