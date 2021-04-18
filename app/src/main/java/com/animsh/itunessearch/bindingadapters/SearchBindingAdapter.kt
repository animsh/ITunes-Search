package com.animsh.itunessearch.bindingadapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Created by animsh on 4/18/2021.
 */
class SearchBindingAdapter {
    companion object {
        @BindingAdapter("android:loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imageView: ImageView, imageUrl: String) {
            try {
//                imageView.alpha = 0f
                Glide.with(imageView.context).load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
//                            imageView.animate().setDuration(300).alpha(1f).start()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
//                            imageView.animate().setDuration(300).alpha(1f).start()
                            return false
                        }
                    }).into(imageView)
            } catch (ignored: Exception) {
            }
        }
    }
}
