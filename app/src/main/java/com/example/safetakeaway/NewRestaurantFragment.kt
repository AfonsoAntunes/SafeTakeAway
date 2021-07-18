package com.example.safetakeaway

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.safetakeaway.databinding.NewRestaurantFragmentBinding
import com.google.android.material.snackbar.Snackbar

class NewRestaurantFragment : Fragment() {

    private var _binding: NewRestaurantFragmentBinding? = null

    private lateinit var editTextRestaurant: EditText
    private lateinit var editTextCategory: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.new_restaurant_menu

        _binding = NewRestaurantFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextRestaurant = view.findViewById(R.id.editTextRestaurant)
        editTextCategory = view.findViewById(R.id.editTextCategory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun browseListRestaurant() {
        findNavController().navigate(R.id.action_newRestaurantFragment_to_listRestaurantFragment)
    }

    fun create() {
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

        val newRestaurant = Restaurant(name = restaurant, category = category)

        val uri = activity?.contentResolver?.insert(
            ContentProviderTakeAway.RESTAURANT_ADDRESS,
            newRestaurant.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextRestaurant,
                getString(R.string.error_to_create),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.created_successfully,
            Toast.LENGTH_LONG
        ).show()
        browseListRestaurant()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_action -> create()
            R.id.cancel_action -> browseListRestaurant()
            else -> return false
        }

        return true
    }
}