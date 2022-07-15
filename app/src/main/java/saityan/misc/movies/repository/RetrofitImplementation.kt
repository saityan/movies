package saityan.misc.movies.repository

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitImplementation {

    private val baseUrl = "https://api.themoviedb.org/3/"

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                .create(
                    GsonBuilder()
                    .setLenient()
                    .create()))
            .client(createOkHttpClient(CustomInterceptor()))
            .build()
            .create(RetrofitAPI::class.java)
    }

    private fun createOkHttpClient(interceptor: okhttp3.Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    fun getPopularMovies(api_key: String, callback: Callback<ServerResponseData>) {
        api.getPopularMovies(api_key).enqueue(callback)
    }

    inner class CustomInterceptor : okhttp3.Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}
