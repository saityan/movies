package saityan.misc.movies.repository

import com.google.gson.annotations.SerializedName

data class ServerResponseData(
    @field:SerializedName("page") val page : Int?,
    @field:SerializedName("results") val results : List<Movie>,
)
