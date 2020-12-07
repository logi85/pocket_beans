package de.seriousdonkey.pocketbeans.app.ui

import android.view.Gravity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class Adapter {
    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null && imageUrl.trim() != "") {
                Picasso.get()
                        .load(imageUrl)
                        .fit()
                        .centerCrop()
                        .into(view)
            }
        }
    }
}