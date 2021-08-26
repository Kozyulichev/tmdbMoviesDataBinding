package com.example.tmdbmovies

import com.example.tmdbmovies.data.Movie

sealed class AppState {

    data class Success(val popularFilmData: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}