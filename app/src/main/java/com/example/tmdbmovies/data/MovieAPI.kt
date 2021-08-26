package com.example.tmdbmovies.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/popular?")
    suspend fun getMovieRetrofit(
        @Query("api_key") token: String,
        @Query("language") language: String,
    ): Movie
    @GET("movie/{id}/credits?")
    suspend fun getActors(
        @Path("id") filmId: Int?,
        @Query("api_key") token: String,
        @Query("language") language: String
    ):Actors
}