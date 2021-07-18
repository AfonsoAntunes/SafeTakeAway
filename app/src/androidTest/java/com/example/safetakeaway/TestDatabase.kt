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

    private fun getRestaurantTable(db: SQLiteDatabase) = RestaurantTable(db)
    private fun getPlatesTable(db: SQLiteDatabase) = PlatesTable(db)
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
    fun getCreateRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Tiago's Pizza", category = "Italian")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val restaurantId = getRestaurantDB(restaurantTable, restaurant.id)

        assertEquals(restaurant, restaurantId)
        db.close()
    }

    @Test
    fun getReadRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Central", category = "Italian")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val restaurantId = getRestaurantDB(restaurantTable, restaurant.id)

        assertEquals(restaurant, restaurantId)
        db.close()
    }

    @Test
    fun getUpdateRestaurant() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Sardinha", category = "Portuguese")
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

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Test", category = "Test")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val deletedData = restaurantTable.delete("${BaseColumns._ID}=?", arrayOf(restaurant.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreatePlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Telepizza", category = "Italian")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Bolognese", category = "Pastas", price = 7.99,  restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val platesId = getPlatesDB(platesTable, plate.id)

        assertEquals(plate, platesId)
        db.close()
    }

    @Test
    fun getReadPlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "O Ferrinho", category = "Regional")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Roasted Lamb", category = "Meat", price = 7.99, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val platesId = getPlatesDB(platesTable, plate.id)

        assertEquals(plate, platesId)
        db.close()
    }

    @Test
    fun getUpdatePlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Sardinha", category = "Portuguese")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Grilled Sardines", category = "Summer Season Fish", price = 9.99, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        plate.category = "Fish"
        plate.price = 7.49

        val changedData = platesTable.update(plate.toContentValues(), "${BaseColumns._ID}=?", arrayOf(plate.id.toString()))
        assertEquals(1, changedData)

        db.close()
    }

    @Test
    fun getDeletePlate() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Test", category = "Test")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Test0", category = "Test", price = 5.00, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val deletedData = platesTable.delete("${BaseColumns._ID}=?", arrayOf(plate.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreateUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", city = "Viseu", email = "afonsoantunes@mail.com", phoneNumber = "969696969")
        user.id = insertUser(userTable, user)

        val userId = getUserDB(userTable, user.id)

        assertEquals(user, userId)
        db.close()
    }

    @Test
    fun getReadUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", city = "Viseu", email = "afonsoantunes@mail.com", phoneNumber = "969696969")
        user.id = insertUser(userTable, user)

        val userId = getUserDB(userTable, user.id)

        assertEquals(user, userId)
        db.close()
    }

    @Test
    fun getUpdateUser() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", city = "Viseu", email = "afonsoantunes@mail.com", phoneNumber = "969696969")
        user.id = insertUser(userTable, user)

        user.address = "Rua Soeiro Viegas"
        user.city = "Guarda"
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

        val userTable = getUserTable(db)
        val user = User(name = "Afonso Antunes", gender = "Masculino", address = "Rua Francisco Sá Carneiro", city = "Guarda", email = "afonsoantunes@mail.com", phoneNumber = "969696969")
        user.id = insertUser(userTable, user)

        val deletedData = userTable.delete("${BaseColumns._ID}=?", arrayOf(user.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun getCreateOrder() {
        val db = getDbTakeAwayOpenHelper().writableDatabase

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Por do Sol", category = "Arabic")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Durum", category = "Meat", price = 5.99, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val userTable = getUserTable(db)
        val user = User(name = "Joaquim Costa", gender = "Masculino", address = "Quinta do Pina", city = "Guarda", email = "joaquimcosta@mail.com", phoneNumber = "969696969")
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

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "S. Vicente", category = "Arabic")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Box", category = "Meat", price = 3.49, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val userTable = getUserTable(db)
        val user = User(name = "Carlos Domingos", gender = "Masculino", address = "Vila Cortez do Mondego", city = "Guarda", email = "carlosdomingos@mail.com", phoneNumber = "929292929")
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

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Sardinha", category = "Portuguese")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Grilled Sardines", category = "Summer Season Fish", price = 9.99, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val userTable = getUserTable(db)
        val user = User(name = "Fátima Santiago", gender = "Feminino", address = "Cerdeira", city = "Guarda", email = "fatimasantiago@mail.com", phoneNumber = "909090909")
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

        val restaurantTable = getRestaurantTable(db)
        val restaurant = Restaurant(name = "Test", category = "Test")
        restaurant.id = insertRestaurant(restaurantTable, restaurant)

        val platesTable = getPlatesTable(db)
        val plate = Plates(name = "Test0", category = "Test", price = 5.00, restaurantId = restaurant.id)
        plate.id = insertPlates(platesTable, plate)

        val userTable = getUserTable(db)
        val user = User(name = "Test1", gender = "Other", address = "Test1", city = "Test1", email = "test2021@mail.com", phoneNumber = "100100100")
        user.id = insertUser(userTable, user)

        val orderTable = getOrderTable(db)
        val order = Order(totalPrice = 10.00, date = Date(2021-7-9), paymentMethod = "Test2", restaurantId = restaurant.id, platesId = plate.id, userId = user.id /*, restaurantName = restaurant.name, platesName = plate.name, userName = user.name */)
        order.id = insertOrder(orderTable, order)

        val deletedData = orderTable.delete("${BaseColumns._ID}=?", arrayOf(order.id.toString()))
        assertEquals(1, deletedData)

        db.close()
    }

}