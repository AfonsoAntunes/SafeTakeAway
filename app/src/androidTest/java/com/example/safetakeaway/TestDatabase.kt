package com.example.safetakeaway

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

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
    private fun getUserTable(db: SQLiteDatabase) = UserTable(db)
    private fun getOrderTable(db: SQLiteDatabase) = OrderTable(db)

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
        // getAppContext().deleteDatabase(DbTakeAwayOpenHelper.DATABASE_NAME)
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

    @Test
    fun getDeleteCity() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Test")
        city.id = insertCity(cityTable, city)

        val deletedData = cityTable.delete("${BaseColumns._ID}=?", arrayOf(city.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreateUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Viseu")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", email = "afonsoantunes@mail.com", phoneNumber = "969696969", cityId = city.id)
        user.id = insertUser(userTable, user)

        val userId = getUserDB(userTable, user.id)

        assertEquals(user, userId)
        db.close()
    }

    @Test
    fun getReadUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Viseu")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", email = "afonsoantunes@mail.com", phoneNumber = "969696969", cityId = city.id)
        user.id = insertUser(userTable, user)

        val userId = getUserDB(userTable, user.id)

        assertEquals(user, userId)
        db.close()
    }

    @Test
    fun getUpdateUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Viseu")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", email = "afonsoantunes@mail.com", phoneNumber = "969696969", cityId = city.id)
        user.id = insertUser(userTable, user)

        user.address = "Rua Soeiro Viegas"
        user.phoneNumber = "929292929"

        val changedData = userTable.update(user.toContentValues(), "${BaseColumns._ID}=?", arrayOf(user.id.toString()))
        assertEquals(1, changedData)

        val userId = getUserDB(userTable, user.id)

        assertEquals(user, userId)
        db.close()
    }

    @Test
    fun getDeleteUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val cityTable = getCityTable(db)
        val city = City(city = "Test1")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", email = "afonsoantunes@mail.com", phoneNumber = "969696969", cityId = city.id)
        user.id = insertUser(userTable, user)

        val deletedData = userTable.delete("${BaseColumns._ID}=?", arrayOf(user.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreateOrder() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Árabe")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Por do Sol", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Durum", price = 5.99, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val cityTable = getCityTable(db)
        val city = City(city = "Guarda")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Joaquim Costa", gender = "Masculino", address = "Quinta do Pina", email = "joaquimcosta@mail.com", phoneNumber = "969696969", cityId = city.id)
        user.id = insertUser(userTable, user)

        val orderTable = getOrderTable(db)
        val order = Order(totalPrice = 17.49, date = Date(2020-8-2), paymentMethod = "Dinheiro", restaurantId = restaurant.id, platesId = plate.id, userId = user.id /*, restaurantName = restaurant.name, platesName = plate.name, userName = user.name */)
        order.id = insertOrder(orderTable, order)

        val orderId = getOrderDB(orderTable, order.id)

        assertEquals(order, orderId)
        db.close()
    }

    @Test
    fun getReadOrder() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Árabe")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "S. Vicente", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Box", price = 3.49, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val cityTable = getCityTable(db)
        val city = City(city = "Vila Cortez do Mondego")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Carlos Domingos", gender = "Masculino", address = "Vila Cortez do Mondego", email = "carlosdomingos@mail.com", phoneNumber = "929292929", cityId = city.id)
        user.id = insertUser(userTable, user)

        val orderTable = getOrderTable(db)
        val order = Order(totalPrice = 17.49, date = Date(2020-10-10), paymentMethod = "Dinheiro", restaurantId = restaurant.id, platesId = plate.id, userId = user.id/* , restaurantName = restaurant.name, platesName = plate.name, userName = user.name */)
        order.id = insertOrder(orderTable, order)

        val orderId = getOrderDB(orderTable, order.id)

        assertEquals(order, orderId)
        db.close()
    }

    @Test
    fun getUpdateOrder() {
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

        val cityTable = getCityTable(db)
        val city = City(city = "Cerdeira")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Fátima Santiago", gender = "Feminino", address = "Cerdeira", email = "fatimasantiago@mail.com", phoneNumber = "909090909", cityId = city.id)
        user.id = insertUser(userTable, user)

        val orderTable = getOrderTable(db)
        val order = Order(totalPrice = 30.00, date = Date(2021-6-20), paymentMethod = "MB Way", restaurantId = restaurant.id, platesId = plate.id, userId = user.id /*, restaurantName = restaurant.name, platesName = plate.name, userName = user.name */)
        order.id = insertOrder(orderTable, order)

        order.paymentMethod = "Cartão de Crédito"

        val changedData = orderTable.update(order.toContentValues(), "${BaseColumns._ID}=?", arrayOf(order.id.toString()))
        assertEquals(1, changedData)

        db.close()
    }

    @Test
    fun getDeleteOrder() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val categoryTable = getCategoryTable(db)
        val category = Category(type = "Test")
        category.id = insertCategory(categoryTable, category)

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Test", categoryId = category.id)
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Test0", price = 5.00, categoryId = category.id, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val cityTable = getCityTable(db)
        val city = City(city = "Test1")
        city.id = insertCity(cityTable, city)

        val userTable = getUserTable(db)
        val user = User(name = "Test1", gender = "Other", address = "Test1", email = "test2021@mail.com", phoneNumber = "100100100", cityId = city.id)
        user.id = insertUser(userTable, user)

        val orderTable = getOrderTable(db)
        val order = Order(totalPrice = 10.00, date = Date(2021-7-9), paymentMethod = "Test2", restaurantId = restaurant.id, platesId = plate.id, userId = user.id /*, restaurantName = restaurant.name, platesName = plate.name, userName = user.name */)
        order.id = insertOrder(orderTable, order)

        val deletedData = orderTable.delete("${BaseColumns._ID}=?", arrayOf(order.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

}