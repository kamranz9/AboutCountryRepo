package com.assignment.aboutcountryproject.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class CustomViewBinding {

    @BindingAdapter(value = ["setAdapter"])
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
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