package com.example.safetakeaway

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.safetakeaway.AdapterPlates.ViewHolderPlates
import androidx.recyclerview.widget.RecyclerView

class AdapterPlates : RecyclerView.Adapter<ViewHolderPlates>() {
    var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPlates (itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
        private val textViewCategory = itemView.findViewById<TextView>(R.id.textViewSubCategory)
        private val textViewPrice = itemView.findViewById<TextView>(R.id.textViewPrice)
        private val textViewRestaurantId = itemView.findViewById<TextView>(R.id.textViewRestaurantID)

        private lateinit var plates: Plates

        init {
            itemView.setOnClickListener(this)
        }

        fun updatePlates(plates: Plates) {
            this.plates = plates

            val name = plates.name
            val price = plates.price.toString()
            val restaurantId = plates.restaurantId.toString()

            textViewName.text = "Plate: $name"
            textViewCategory.text = plates.category
            textViewPrice.text = "Price: $price"
            textViewRestaurantId.text = "Restaurant ID: $restaurantId"
        }

        override fun onClick(v: View?) {
            selected?.desSelected()
            select()
        }

        private fun select() {
            selected = this
            itemView.setBackgroundResource(R.color.select_color)
            AppData.selectedPlates = plates
            AppData.activity.updateMenuListPlates(true)
        }

        private fun desSelected() {
            selected = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selected : ViewHolderPlates? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPlates {
        val platesItem = AppData.fragment.layoutInflater.inflate(R.layout.plates_item, parent, false)

        return ViewHolderPlates(platesItem)
    }

    override fun onBindViewHolder(holder: ViewHolderPlates, position: Int) {
        cursor!!.moveToPosition(position)
        holder.updatePlates(Plates.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}