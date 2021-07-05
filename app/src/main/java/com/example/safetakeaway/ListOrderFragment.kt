package com.example.safetakeaway

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.databinding.ListOrderFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListOrderFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: ListOrderFragmentBinding? = null
    private var adapterOrder : AdapterOrder? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.list_order_menu

        _binding = ListOrderFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleViewOrder = view.findViewById<RecyclerView>(R.id.recyclerViewOrder)
        adapterOrder = AdapterOrder()
        recycleViewOrder.adapter = adapterOrder
        recycleViewOrder.layoutManager = LinearLayoutManager(requireContext())

        /* binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        } */

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_ORDER, null, this)
    }

    fun processMenuOption(item: MenuItem): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderTakeAway.ORDER_ADDRESS,
            OrderTable.ALL_FIELD,
            null,
            null,
            OrderTable.USER_ID
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterOrder!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterOrder!!.cursor = null
    }

    companion object {
        const val ID_LOADER_MANAGER_ORDER = 0
    }
}