package com.example.tmdbmovies.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("popular?")
    suspend fun getMovieRetrofit(
        @Query("api_key") token: String,
        @Query("language") language: String,
    ): Movie
}