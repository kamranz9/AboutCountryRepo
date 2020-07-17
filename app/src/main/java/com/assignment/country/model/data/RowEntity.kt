package com.assignment.country.model.data

import android.view.View
import android.widget.ImageView
import android.widget.Toast
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

            var url1: String? = ""
            if (url != null && url.contains(":")) {
                url1 = url.split(":")[0]
                url1 = url1.replace("http", "https")
                url1 = url1+":"+ url.split(":")[1]
            }

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

    /**
     * On Item Click of Row Item
     * @param v parameter view is the view of row click - Used from XML
     */
    fun onItemClick(v: View?) {
        if (title != null) {
            showToast(title,v)
        }
    }


    /**
     * Method to show toast in activity
     */
    private fun showToast(title: String?, v: View?) {
        Toast.makeText(v?.context, title, Toast.LENGTH_SHORT).show()
    }

}