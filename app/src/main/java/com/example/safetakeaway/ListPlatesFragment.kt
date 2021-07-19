package com.example.safetakeaway

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.databinding.ListPlatesFragmentBinding

class ListPlatesFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: ListPlatesFragmentBinding? = null
    private var adapterPlates : AdapterPlates? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.list_plates_menu

        _binding = ListPlatesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleViewPlates = view.findViewById<RecyclerView>(R.id.recyclerViewPlates)
        adapterPlates = AdapterPlates()
        recycleViewPlates.adapter = adapterPlates
        recycleViewPlates.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_PLATES, null, this)
    }

    fun browseEditPlates() {
        findNavController().navigate(R.id.action_listPlatesFragment_to_editPlatesFragment)
    }

    fun browseDeletePlates() {
        findNavController().navigate(R.id.action_listPlatesFragment_to_deletePlatesFragment)
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_plates_action -> browseEditPlates()
            R.id.delete_plates_action -> browseDeletePlates()
            else -> return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderTakeAway.PLATES_ADDRESS,
            PlatesTable.ALL_FIELD,
            null,
            null,
            PlatesTable.NAME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterPlates!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterPlates!!.cursor = null
    }

    companion object {
        const val ID_LOADER_MANAGER_PLATES = 0
    }
}