package saityan.misc.movies.view.viewmodel

import saityan.misc.movies.repository.ServerResponseData

sealed class MoviesData {
    data class Success(val serverResponseData: ServerResponseData) : MoviesData()
    data class Error(val error: Throwable) : MoviesData()
    object Loading : MoviesData()
}
