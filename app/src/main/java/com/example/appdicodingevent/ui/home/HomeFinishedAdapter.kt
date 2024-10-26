package com.example.appdicodingevent.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdicodingevent.data.remote.response.ListEventsItem
import com.example.appdicodingevent.databinding.ItemEventCardBinding

class HomeFinishedAdapter(private val onItemClick: (ListEventsItem) -> Unit ): ListAdapter<ListEventsItem, HomeFinishedAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemEventCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(event: ListEventsItem, onItemClick:(ListEventsItem) -> Unit ) {
            Glide.with(binding.imgMediaCover.context)
                .load(event.mediaCover)
                .into(binding.imgMediaCover)
            binding.txtEventCover.text = event.name

            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}