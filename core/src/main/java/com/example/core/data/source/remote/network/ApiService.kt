package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.GetMovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("now_playing?api_key=$api_key")
    suspend fun getMovieNowPlaying(): GetMovieResponse

    @GET("popular?api_key=$api_key")
    suspend fun getMoviePopular(): GetMovieResponse

    @GET("top_rated?api_key=$api_key")
    suspend fun getMovieTopRated(): GetMovieResponse

    companion object {
        private const val api_key = "ced9627fcb3b480331b91e3223e07ac4"
    }
}
