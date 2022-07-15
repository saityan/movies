package saityan.misc.movies.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import saityan.misc.movies.BuildConfig
import saityan.misc.movies.repository.RetrofitImplementation
import saityan.misc.movies.repository.ServerResponseData

class MainViewModel : ViewModel() {

    private val dataObservable: MutableLiveData<MoviesData> = MutableLiveData()

    private val retrofit: RetrofitImplementation = RetrofitImplementation()

    fun getMoviesLiveData(): LiveData<MoviesData> = dataObservable

    fun sendServerRequest() {
        dataObservable.value = MoviesData.Loading
        val apiKey = BuildConfig.MOVIES_API_KEY
        if (apiKey.isBlank()) {
            MoviesData.Error(Throwable("API key parameter is empty"))
        } else {
            retrofit.getPopularMovies(apiKey, callback)
        }
    }

    private val callback = object : Callback<ServerResponseData> {
        override fun onResponse(
            call: Call<ServerResponseData>,
            response: Response<ServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null)
                dataObservable.value = MoviesData.Success(response.body()!!)
            else {
                val message = response.message()
                if (message.isNullOrEmpty())
                    dataObservable.value = MoviesData.Error(Throwable("Unknown error"))
                else
                    dataObservable.value = MoviesData.Error(Throwable(message))
            }
        }

        override fun onFailure(call: Call<ServerResponseData>, t: Throwable) {
            dataObservable.value = MoviesData.Error(t)
        }
    }
}
