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

class DeleteOrderFragment : Fragment() {
    private lateinit var textViewDate: TextView
    private lateinit var textViewUserId: TextView
    private lateinit var textViewRestaurantId: TextView
    private lateinit var textViewPlatesId: TextView
    private lateinit var textViewTotalPrice: TextView
    private lateinit var textViewPaymentMethod: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.delete_order_menu

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.delete_order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewDate = view.findViewById(R.id.textViewDate)
        textViewUserId = view.findViewById(R.id.textViewUserId)
        textViewRestaurantId = view.findViewById(R.id.textViewRestaurantId)
        textViewPlatesId = view.findViewById(R.id.textViewPlatesId)
        textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice)
        textViewPaymentMethod = view.findViewById(R.id.textViewPaymentMethod)

        // val order = AppData.selectedOrder!!
        // textViewDate.setText(order.date)
        // textViewUserId.setText(order.userId)
        // textViewRestaurantId.setText(order.restaurantId)
        // textViewPlatesId.setText(order.platesId)
        // textViewTotalPrice.setText(order.totalPrice)
        // textViewPaymentMethod.setText(order.paymentMethod)
    }

    fun browseListOrder() {
        findNavController().navigate(R.id.action_deleteOrderFragment_to_listOrderFragment)
    }

    fun delete() {
        val uriOrder = Uri.withAppendedPath(
            ContentProviderTakeAway.ORDER_ADDRESS,
            AppData.selectedOrder!!.id.toString()
        )

        val regists = activity?.contentResolver?.delete(
            uriOrder,
            null,
            null
        )

        if (regists != 1) {
            Toast.makeText(
                requireContext(),
                R.string.error_delete_order,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.order_deleted_successfully,
            Toast.LENGTH_LONG
        ).show()
        browseListOrder()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.confirm_delete_order_action -> delete()
            R.id.cancel_delete_order_action -> browseListOrder()
            else -> return false
        }

        return true
    }
}