package com.example.tmdbmovies.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres(

    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String

) : Parcelable