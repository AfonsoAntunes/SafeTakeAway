package com.example.safetakeaway

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class DeleteRestaurantFragment : Fragment() {
    private lateinit var textViewRestaurant: TextView
    private lateinit var textViewCategory: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.delete_restaurant_menu

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.delete_restaurant_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewRestaurant = view.findViewById(R.id.textViewRestaurant)
        textViewCategory = view.findViewById(R.id.textViewCategory)

        val restaurant = AppData.selectedRestaurant!!
        textViewRestaurant.text = restaurant.name
        textViewCategory.text = restaurant.category
    }

    fun browseListRestaurant() {
        findNavController().navigate(R.id.action_deleteRestaurantFragment_to_listRestaurantFragment)
    }

    fun delete() {
        val uriRestaurant = Uri.withAppendedPath(
            ContentProviderTakeAway.RESTAURANT_ADDRESS,
            AppData.selectedRestaurant!!.id.toString()
        )

        val regists = activity?.contentResolver?.delete(
            uriRestaurant,
            null,
            null
        )

        if (regists != 1) {
            Toast.makeText(
                requireContext(),
                R.string.error_delete_restaurant,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.restaurant_deleted_successfully,
            Toast.LENGTH_LONG
        ).show()
        browseListRestaurant()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.confirm_delete_restaurant_action -> delete()
            R.id.cancel_delete_restaurant_action -> browseListRestaurant()
            else -> return false
        }

        return true
    }
}