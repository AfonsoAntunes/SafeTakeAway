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

class DeletePlatesFragment : Fragment() {
    private lateinit var textViewRestaurantID: TextView
    private lateinit var textViewName: TextView
    private lateinit var textViewCategory: TextView
    private lateinit var textViewPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.delete_plates_menu

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.delete_plates_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewRestaurantID = view.findViewById(R.id.textViewRestaurantID)
        textViewName = view.findViewById(R.id.textViewName)
        textViewCategory = view.findViewById(R.id.textViewCategory)
        textViewPrice = view.findViewById(R.id.textViewPrice)

        val plates = AppData.selectedPlates!!
        textViewRestaurantID.text = plates.restaurantId.toString()
        textViewName.text = plates.name
        textViewCategory.text = plates.category
        textViewPrice.text = plates.price.toString()
    }

    fun browseListPlates() {
        findNavController().navigate(R.id.action_deletePlatesFragment_to_listPlatesFragment)
    }

    fun delete() {
        val uriPlates = Uri.withAppendedPath(
            ContentProviderTakeAway.PLATES_ADDRESS,
            AppData.selectedPlates!!.id.toString()
        )

        val regists = activity?.contentResolver?.delete(
            uriPlates,
            null,
            null
        )

        if (regists != 1) {
            Toast.makeText(
                requireContext(),
                R.string.error_to_delete,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.deleted_successfully,
            Toast.LENGTH_LONG
        ).show()
        browseListPlates()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_action -> delete()
            R.id.cancel_action -> browseListPlates()
            else -> return false
        }

        return true
    }
}