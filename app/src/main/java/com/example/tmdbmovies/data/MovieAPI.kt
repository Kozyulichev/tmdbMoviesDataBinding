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
    ): Actors

    @GET("genre/movie/list?")
    suspend fun getCategory(
        @Query("api_key") token: String,
        @Query("language") language: String
    ): Category

    @GET("movie/popular?")
    suspend fun getMovieByCategoryRetrofit(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("with_genres")category: Int
    ): Movie
}