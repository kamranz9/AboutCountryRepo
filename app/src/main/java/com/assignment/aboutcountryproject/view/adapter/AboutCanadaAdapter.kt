package com.assignment.aboutcountryproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.aboutcountryproject.databinding.ItemCanadaBinding
import com.assignment.aboutcountryproject.model.data.RowEntity

class AboutCanadaAdapter : ListAdapter<RowEntity, AboutCanadaAdapter.RowEntityViewHolder>(Companion) {

    class RowEntityViewHolder(val binding: ItemCanadaBinding) : RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<RowEntity>() {
        override fun areItemsTheSame(oldItem: RowEntity, newItem: RowEntity): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: RowEntity, newItem: RowEntity): Boolean = oldItem.title == newItem.title
    }

    @Nullable
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowEntityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCanadaBinding.inflate(layoutInflater)

        return RowEntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowEntityViewHolder, position: Int) {
        val currentRowEntity = getItem(position)
        holder.binding.row = currentRowEntity
        holder.binding.executePendingBindings()
    }

}