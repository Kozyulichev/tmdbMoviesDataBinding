package com.example.tmdbmovies.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "eaa99c6fd63b5488a4816703a57c78f7"
private const val BASE_URL = "https://api.themoviedb.org/3/"

interface Repository {
    suspend fun getMovie(language: String): Movie
    suspend fun getActors(filmId: Int?, language: String): Actors
    suspend fun getCategory(language: String): Category
    suspend fun getMovieByCategory(language: String, category: Int): Movie
}

class RepositoryImpl : Repository {
    private val movieAPI = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPI::class.java)


    override suspend fun getMovie(language: String): Movie {
        return movieAPI.getMovieRetrofit(API_KEY, language)
    }

    override suspend fun getActors(filmId: Int?, language: String): Actors {
        return movieAPI.getActors(filmId, API_KEY, language)
    }

    override suspend fun getCategory(language: String): Category {
        return movieAPI.getCategory(API_KEY, language)
    }

    override suspend fun getMovieByCategory(language: String, category: Int): Movie {
        return movieAPI.getMovieByCategoryRetrofit(API_KEY, language, category)
    }
}

