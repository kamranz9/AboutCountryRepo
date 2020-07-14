package com.assignment.aboutcountryproject.model.data

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

data class RowEntity(var title: String?, var description: String?, var imageHref: String?) {
    object DataBindingAdapter {
        // Loading Image using Glide Library.
        @BindingAdapter("android:imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String?) {
            imageView.visibility = View.GONE

            var url1: String? = url?.split(":")?.get(0)
            url1 = url1?.replace("http", "https")
            url1 = url1+":"+url?.split(":")?.get(1)

            Glide.with(imageView.context)
                .load(url1)
                .listener(object :
                    RequestListener<String?, GlideDrawable?> {
                    override fun onException(
                        e: Exception?,
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

}