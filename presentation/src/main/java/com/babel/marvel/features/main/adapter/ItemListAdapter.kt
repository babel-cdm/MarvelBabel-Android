package com.babel.marvel.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.babel.marvel.databinding.LayoutItemsBinding
import com.babel.marvel.domain.viewstate.Items

class ItemListAdapter(
    private val userItems: List<Items>
) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    lateinit var binding: LayoutItemsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userItems[position])
    }

    // this method is giving the size of the list
    override fun getItemCount(): Int = userItems.size

    // the class is hodling the list view
    class ViewHolder(private val binding: LayoutItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: Items) {
            binding.tvItemDescription.text = item.name
        }
    }
}
