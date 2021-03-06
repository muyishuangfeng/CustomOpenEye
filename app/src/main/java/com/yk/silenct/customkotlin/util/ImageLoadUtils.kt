package com.yk.silenct.customkotlin.util

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silenct.customkotlin.R

class ImageLoadUtils {

    //静态方法
    companion object {
        fun display(context: Context, imageView: ImageView, url:String){
            if (imageView == null) {
                throw IllegalArgumentException("argument error")
            }
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .crossFade()
                    .into(imageView)
        }

        fun displayHigh(context: Context,imageView: AppCompatImageView?,url:String){
            if (imageView == null) {
                throw IllegalArgumentException("argument error")
            }
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .into(imageView)
        }
    }
}