package com.babel.marvel.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.babel.marvel.R
import com.babel.marvel.databinding.LayoutCharacterBinding
import com.babel.marvel.domain.Constants
import com.babel.marvel.domain.viewstate.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ListCharacterAdapter(
    private val userList: List<Character>,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<ListCharacterAdapter.ViewHolder>() {

    lateinit var binding: LayoutCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
        holder.itemView.setOnClickListener {
            listener(userList[position].id)
        }
    }

    override fun getItemCount(): Int = userList.size

    class ViewHolder(private val binding: LayoutCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(character: Character) {
            binding.tvCharacterTitle.text = character.name
            binding.tvCharacterDescription.text = character.description
            Glide.with(itemView)
                .load(character.thumbnail.path + Constants.DOT_SYMBOL + character.thumbnail.extension)
                .error(R.drawable.ic_marvel)
                .placeholder(R.drawable.ic_marvel)
                .transform(RoundedCorners(RADIUS_CORNER))
                .into(binding.ivCharacterImage)
        }
    }

    companion object {
        const val RADIUS_CORNER = 16
    }
}
