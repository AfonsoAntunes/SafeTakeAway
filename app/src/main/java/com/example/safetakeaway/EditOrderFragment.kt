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
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController

class EditOrderFragment : Fragment() {
    private lateinit var textViewDate: TextView
    private lateinit var textViewUserId: TextView
    private lateinit var textViewRestaurantId: TextView
    private lateinit var textViewPlatesId: TextView
    private lateinit var textViewTotalPrice: TextView
    private lateinit var editTextPaymentMethod: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.edit_order_menu

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewDate = view.findViewById(R.id.textViewDate)
        textViewUserId = view.findViewById(R.id.textViewUserId)
        textViewRestaurantId = view.findViewById(R.id.textViewRestaurantId)
        textViewPlatesId = view.findViewById(R.id.textViewPlatesId)
        textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice)
        editTextPaymentMethod = view.findViewById(R.id.editTextPaymentMethod)

        val order = AppData.selectedOrder!!
        textViewDate.text = order.date.toString()
        textViewUserId.text = order.userId.toString()
        textViewRestaurantId.text = order.restaurantId.toString()
        textViewPlatesId.text = order.platesId.toString()
        textViewTotalPrice.text = order.totalPrice.toString()
        editTextPaymentMethod.setText(AppData.selectedOrder!!.paymentMethod)
    }

    fun browseListOrder() {
        findNavController().navigate(R.id.action_editOrderFragment_to_listOrderFragment)
    }

    fun save() {
        val date = textViewDate.text
        val userId = textViewUserId.text
        val restaurantId = textViewRestaurantId.text
        val platesId = textViewPlatesId.text
        val totalPrice = textViewTotalPrice.text

        val paymentMethod = editTextPaymentMethod.text.toString()
        if (paymentMethod.isEmpty()) {
            editTextPaymentMethod.setError(getString(R.string.fill_payment_method))
            editTextPaymentMethod.requestFocus()
            return
        }

        val editOrder = AppData.selectedOrder!!
        editOrder.paymentMethod = paymentMethod

        val uriOrder = Uri.withAppendedPath(
            ContentProviderTakeAway.ORDER_ADDRESS,
            editOrder.id.toString()
        )

        val regists = activity?.contentResolver?.update(
            uriOrder,
            editOrder.toContentValues(),
            null,
            null
        )

        if (regists != 1) {
            Toast.makeText(
                requireContext(),
                R.string.error_edit_order,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.order_edited_successfully,
            Toast.LENGTH_LONG
        ).show()
        browseListOrder()
    }

    fun processMenuOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_edit_order_action -> save()
            R.id.cancel_edit_order_action -> browseListOrder()
            else -> return false
        }

        return true
    }
}