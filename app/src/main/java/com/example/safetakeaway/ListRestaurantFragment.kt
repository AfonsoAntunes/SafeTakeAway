package com.example.safetakeaway

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.AppData.Companion.activity
import com.example.safetakeaway.databinding.ListRestaurantFragmentBinding

class ListRestaurantFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: ListRestaurantFragmentBinding? = null
    private var adapterRestaurant : AdapterRestaurant? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.list_restaurant_menu

        _binding = ListRestaurantFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleViewRestaurant = view.findViewById<RecyclerView>(R.id.recyclerViewRestaurant)
        adapterRestaurant = AdapterRestaurant()
        recycleViewRestaurant.adapter = adapterRestaurant
        recycleViewRestaurant.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_RESTAURANT, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderTakeAway.RESTAURANT_ADDRESS,
            RestaurantTable.ALL_FIELD,
            null,
            null,
            RestaurantTable.NAME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterRestaurant!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterRestaurant!!.cursor = null
    }

    companion object {
        const val ID_LOADER_MANAGER_RESTAURANT = 0
    }
}