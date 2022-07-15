package saityan.misc.movies.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("discover/movie")
    fun getPopularMovies(@Query("api_key") api_key: String): Call<ServerResponseData>
}
