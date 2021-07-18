package com.example.safetakeaway

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.AdapterRestaurant.ViewHolderRestaurant

class AdapterRestaurant : RecyclerView.Adapter<ViewHolderRestaurant>() {
    var cursor: Cursor? = null
    get() = field
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolderRestaurant (itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewRestaurant = itemView.findViewById<TextView>(R.id.textViewRestaurant)
        private val textViewCategory = itemView.findViewById<TextView>(R.id.textViewCategory)

        private lateinit var restaurant: Restaurant

        init {
            itemView.setOnClickListener(this)
        }

        fun updateRestaurant(restaurant: Restaurant) {
            this.restaurant = restaurant

            textViewRestaurant.text = restaurant.name
            textViewCategory.text = restaurant.category
        }

        override fun onClick(v: View?) {
            selected?.desSelected()
            select()
        }

        private fun select() {
            selected = this
            itemView.setBackgroundResource(R.color.select_color)
            AppData.selectedRestaurant = restaurant
            AppData.activity.updateMenuListRestaurant(true)
        }

        private fun desSelected() {
            selected = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selected : ViewHolderRestaurant? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRestaurant {
        val restaurantItem = AppData.fragment.layoutInflater.inflate(R.layout.restaurant_item, parent, false)

        return ViewHolderRestaurant(restaurantItem)
    }

    override fun onBindViewHolder(holder: ViewHolderRestaurant, position: Int) {
        cursor!!.moveToPosition(position)
        holder.updateRestaurant(Restaurant.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}