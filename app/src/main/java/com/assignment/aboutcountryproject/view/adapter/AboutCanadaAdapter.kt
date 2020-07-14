package com.assignment.aboutcountryproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.aboutcountryproject.databinding.ItemCanadaBinding
import com.assignment.aboutcountryproject.model.data.RowEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

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


    // Loading Image using Glide Library.
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        imageView.visibility = View.GONE
        Glide.with(imageView.context).load(url).listener(object :
            RequestListener<String?, GlideDrawable?> {
            override fun onException(
                e: Exception,
                model: String?,
                target: Target<GlideDrawable?>,
                isFirstResource: Boolean
            ): Boolean {
                imageView.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: GlideDrawable?,
                model: String?,
                target: Target<GlideDrawable?>,
                isFromMemoryCache: Boolean,
                isFirstResource: Boolean
            ): Boolean {
                imageView.visibility = View.VISIBLE
                return false
            }
        }).into(imageView)
    }

}