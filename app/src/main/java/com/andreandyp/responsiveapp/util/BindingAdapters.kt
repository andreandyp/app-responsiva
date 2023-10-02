package com.andreandyp.responsiveapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.andreandyp.responsiveapp.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Picasso
        .get()
        .load(url)
        .error(R.drawable.ic_baseline_cloud_off_24)
        .placeholder(R.drawable.ic_baseline_cloud_download_24)
        .into(imageView)
}