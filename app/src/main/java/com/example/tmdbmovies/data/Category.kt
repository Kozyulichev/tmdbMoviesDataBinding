package com.example.tmdbmovies.data

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("genres") var genres : List<Genres>
)