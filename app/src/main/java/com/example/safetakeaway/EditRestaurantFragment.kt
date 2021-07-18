package com.example.safetakeaway

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EditRestaurantFragment : Fragment() {
    private lateinit var editTextRestaurant: TextView
    private lateinit var editTextCategory: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.edit_restaurant_menu

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_restaurant_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextRestaurant = view.findViewById(R.id.editTextRestaurant)
        editTextCategory = view.findViewById(R.id.editTextCategory)

        val restaurant = AppData.selectedRestaurant!!
        editTextRestaurant.text = restaurant.name
        editTextCategory.text = restaurant.category
    }

    fun browseListRestaurant() {
        findNavController().navigate(R.id.action_editRestaurantFragment_to_listRestaurantFragment)
    }

    fun save() {
        val restaurant = editTextRestaurant.text.toString()
        if (restaurant.isEmpty()) {
            editTextRestaurant.error = getString(R.string.fill_this_field)
            editTextRestaurant.requestFocus()
            return
        }

        val category = editTextCategory.text.toString()
        if (category.isEmpty()) {
            editTextCategory.error = getString(R.string.fill_this_field)
            editTextCategory.requestFocus()
            return
        }

        val editRestaurant = AppData.selectedRestaurant!!
        editRestaurant.name = restaurant
        editRestaurant.category = category

        val uriRestaurant = Uri.withAppendedPath(
            ContentProviderTakeAway.RESTAURANT_ADDRESS,
            editRestaurant.id.toString()
        )

        val regists = activity?.contentResolver?.update(
            uriRestaurant,
            editRestaurant.toContentValues(),
            null,
            null
        )

        if (regists != 1) {
            Toast.makeText(
                requireContext(),
                R.string.error_to_edit,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.edited_successfully,
            Toast.LENGTH_LONG
        ).show()
        browseListRestaurant()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_edit_restaurant_action -> save()
            R.id.cancel_edit_restaurant_action -> browseListRestaurant()
            else -> return false
        }

        return true
    }
}