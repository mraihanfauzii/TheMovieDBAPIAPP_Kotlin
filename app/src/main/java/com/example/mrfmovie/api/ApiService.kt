package com.example.mrfmovie.api

import com.example.mrfmovie.api.response.DetailsMovieResponse
import com.example.mrfmovie.api.response.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page : Int) : Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int) : Call<DetailsMovieResponse>
}