package com.example.safetakeaway

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.AdapterOrder.ViewHolderOrder

class AdapterOrder (var cursor : Cursor? = null) : RecyclerView.Adapter<ViewHolderOrder>() {
    class ViewHolderOrder (itemView : View) : RecyclerView.ViewHolder(itemView) {

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