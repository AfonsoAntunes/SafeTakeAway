package com.example.safetakeaway

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.AdapterOrder.ViewHolderOrder

class AdapterOrder : RecyclerView.Adapter<ViewHolderOrder>() {
    var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderOrder (itemView : View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val textViewTotalPrice = itemView.findViewById<TextView>(R.id.textViewTotalPrice)
        private val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)
        private val textViewPaymentMethod = itemView.findViewById<TextView>(R.id.textViewPaymentMethod)
        private val textViewRestaurantId = itemView.findViewById<TextView>(R.id.textViewRestaurantId)
        private val textViewPlatesId = itemView.findViewById<TextView>(R.id.textViewPlatesId)
        private val textViewUserId = itemView.findViewById<TextView>(R.id.textViewUserId)

        private lateinit var order: Order

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selected?.desSelected()
            select()
        }

        private fun select() {
            selected = this
            itemView.setBackgroundResource(R.color.select_color)
            AppData.selectedOrder = order
            AppData.activity.updateMenuListOrder(true)
        }

        private fun desSelected() {
            selected = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selected : ViewHolderOrder? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOrder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderOrder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}