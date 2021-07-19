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

class EditPlatesFragment : Fragment() {
    private lateinit var editTextName: EditText
    private lateinit var editTextCategory: EditText
    private lateinit var editTextPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.edit_plates_menu

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_plates_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextName = view.findViewById(R.id.editTextName)
        editTextCategory = view.findViewById(R.id.editTextCategory)
        editTextPrice = view.findViewById(R.id.textViewPrice)

        val plates = AppData.selectedPlates!!
        editTextName.setText(plates.name)
        editTextCategory.setText(plates.category)
        editTextPrice.text = plates.price.toString()
    }

    fun browseListPlates() {
        findNavController().navigate(R.id.action_editPlatesFragment_to_listPlatesFragment)
    }

    fun save() {
        val namePlate = editTextName.text.toString()
        if (namePlate.isEmpty()) {
            editTextName.error = getString(R.string.fill_this_field)
            editTextName.requestFocus()
            return
        }

        val category = editTextCategory.text.toString()
        if (category.isEmpty()) {
            editTextCategory.error = getString(R.string.fill_this_field)
            editTextCategory.requestFocus()
            return
        }

        val editPlates = AppData.selectedPlates!!
        editPlates.name = namePlate
        editPlates.category = category

        val uriPlates = Uri.withAppendedPath(
            ContentProviderTakeAway.PLATES_ADDRESS,
            editPlates.id.toString()
        )

        val regists = activity?.contentResolver?.update(
            uriPlates,
            editPlates.toContentValues(),
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
        browseListPlates()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_edit_action -> save()
            R.id.cancel_action -> browseListPlates()
            else -> return false
        }

        return true
    }
}