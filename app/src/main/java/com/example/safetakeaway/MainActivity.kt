package com.example.safetakeaway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.safetakeaway.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var atualMenu = R.menu.main_menu
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        AppData.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(atualMenu, menu)
        this.menu = menu

        if (atualMenu == R.menu.list_order_menu) {
            updateMenuListOrder(false)
        } else if (atualMenu == R.menu.list_restaurant_menu) {
            updateMenuListRestaurant(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val successfulOption = when (item.itemId) {
            R.id.about_action -> {
                Toast.makeText(this, "Safe Take Away v. 1.0", Toast.LENGTH_LONG).show()
                true
            }

            else -> when (atualMenu) {
                R.menu.list_restaurant_menu -> (AppData.fragment as ListRestaurantFragment).processMenuOption(item)
                R.menu.new_restaurant_menu -> (AppData.fragment as NewRestaurantFragment).processMenuOption(item)
                R.menu.edit_restaurant_menu -> (AppData.fragment as EditRestaurantFragment).processMenuOption(item)
                R.menu.list_order_menu -> (AppData.fragment as ListOrderFragment).processMenuOption(item)
                R.menu.edit_order_menu -> (AppData.fragment as EditOrderFragment).processMenuOption(item)
                R.menu.delete_order_menu -> (AppData.fragment as DeleteOrderFragment).processMenuOption(item)
                else -> false
            }
        }
        return if(successfulOption) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun updateMenuListRestaurant(seeButtonsEditDelete : Boolean) {
        menu.findItem(R.id.edit_restaurant_action).setVisible(seeButtonsEditDelete)
        menu.findItem(R.id.delete_restaurant_action).setVisible(seeButtonsEditDelete)
    }

    fun updateMenuListOrder(seeButtonsEditDelete : Boolean) {
        menu.findItem(R.id.edit_order_action).setVisible(seeButtonsEditDelete)
        menu.findItem(R.id.delete_order_action).setVisible(seeButtonsEditDelete)
    }
}