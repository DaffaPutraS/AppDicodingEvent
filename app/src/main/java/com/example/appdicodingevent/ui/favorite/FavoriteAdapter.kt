package com.example.appdicodingevent.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdicodingevent.data.local.entity.FavoriteEvent
import com.example.appdicodingevent.databinding.ItemFavoriteBinding

class FavoriteAdapter(private val onItemClick: (FavoriteEvent) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var favoriteEvent: List<FavoriteEvent> = emptyList()
    fun submitList(favoriteEvent: List<FavoriteEvent>) {
        this.favoriteEvent = favoriteEvent
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = favoriteEvent[position]
        holder.bind(event, onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent, onItemClick: (FavoriteEvent) -> Unit) {
            binding.txtNameUpcoming.text = event.name
            Glide.with(binding.imgUpcoming.context)
                .load(event.mediaCover)
                .into(binding.imgUpcoming)

            itemView.setOnClickListener{ onItemClick(event)}
        }
    }
    override fun getItemCount(): Int = favoriteEvent.size
}