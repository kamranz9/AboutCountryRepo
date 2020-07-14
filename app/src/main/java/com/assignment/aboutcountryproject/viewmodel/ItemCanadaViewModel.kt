package com.assignment.aboutcountryproject.viewmodel

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.assignment.aboutcountryproject.model.data.RowEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class ItemCanadaViewModel(user: RowEntity, context: Context) : ViewModel() {
    private lateinit var user: RowEntity
    lateinit var noimagevisible: ObservableInt

}


fun setImageUrl(imageView: ImageView, url: String?) {
    imageView.visibility = View.GONE
    Glide.with(imageView.context).load(url)
        .listener(object : RequestListener<String?, GlideDrawable?> {
            override fun onException(
                e: Exception?,
                model: String?,
                target: Target<GlideDrawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                imageView.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: GlideDrawable?,
                model: String?,
                target: Target<GlideDrawable?>?,
                isFromMemoryCache: Boolean,
                isFirstResource: Boolean
            ): Boolean {
                imageView.visibility = View.VISIBLE
                return false
            }
        }).into(imageView)
}
