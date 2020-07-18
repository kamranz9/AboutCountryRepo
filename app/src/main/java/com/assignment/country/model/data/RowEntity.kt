package com.assignment.country.model.data

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

data class RowEntity(var title: String?, var description: String?, var imageHref: String?) {
    object DataBindingAdapter {
        // Loading Image using Glide Library.
        @BindingAdapter("android:imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String?) {
            imageView.visibility = View.GONE

            Glide.with(imageView.context)
                .load(url)
                .listener(object :
                    RequestListener<Drawable?> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageView.visibility = View.VISIBLE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageView.visibility = View.GONE
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
            showToast(title, v)
        }
    }


    /**
     * Method to show toast in activity
     */
    private fun showToast(title: String?, v: View?) {
        Toast.makeText(v?.context, title, Toast.LENGTH_SHORT).show()
    }

}