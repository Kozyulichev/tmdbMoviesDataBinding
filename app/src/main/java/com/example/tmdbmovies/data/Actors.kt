package com.example.tmdbmovies.data

import com.google.gson.annotations.SerializedName

data class Actors(
    @SerializedName("id") var id : Int,
    @SerializedName("cast") var cast : List<Cast>,

)
