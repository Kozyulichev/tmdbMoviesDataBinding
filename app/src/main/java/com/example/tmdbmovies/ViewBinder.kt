package com.example.tmdbmovies

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tmdbmovies.data.Result
import com.example.tmdbmovies.ui.main.CircleTransformation
import com.example.tmdbmovies.ui.main.URL_IMAGE
import com.squareup.picasso.Picasso

object ViewBinder {
    @JvmStatic
    @BindingAdapter("isVisible")
    fun setVisible(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.GONE
    }
    @JvmStatic
    @BindingAdapter("urlActors")
    fun loadImageForActors(view: ImageView,urlImage:String?) {
        Picasso.get().load("$URL_IMAGE$urlImage").
        placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .transform(CircleTransformation())
            .into(view)
    }
    @JvmStatic
    @BindingAdapter("url")
    fun loadImage(view: ImageView,urlImage:String?) {
        Picasso.get().load("$URL_IMAGE$urlImage").
        placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(view)
    }
    @JvmStatic
    @BindingAdapter("toString")
    fun doubleToString(view: TextView,vote:Double){
        view.text = vote.toString()
    }
}