package com.example.mrfmovie.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mrfmovie.api.ApiClient
import com.example.mrfmovie.api.ApiService
import com.example.mrfmovie.api.response.DetailsMovieResponse
import com.example.mrfmovie.api.response.MovieListResponse
import com.example.mrfmovie.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {
    private val api : ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    fun getPopularMovies(callback: (List<Result>?) -> Unit, errorCallback: (String) -> Unit) {
        val callMovieApi = api.getPopularMovie(1)
        callMovieApi.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                when (response.code()) {
                    //Successful responses
                    in 200..299 -> {
                        val movieList = response.body()?.results

                        callback(movieList)
                    }
                    //Redirection messages
                    in 300..399 -> {
                        errorCallback("Redirection messages : ${response.code()}")
                    }
                    //Client error responses
                    in 400..499 -> {
                        errorCallback("Client error responses : ${response.code()}")
                    }
                    //Server error responses
                    in 500..599 -> {
                        errorCallback("Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                errorCallback("Error : ${t.message}")
            }

        })
    }

    fun getMovieDetails(movieId: Int, callback: (DetailsMovieResponse?) -> Unit, errorCallback: (String) -> Unit) {
        val callDetailsMovieApi = api.getMovieDetails(movieId)
        callDetailsMovieApi.enqueue(object : Callback<DetailsMovieResponse> {
            override fun onResponse(
                call: Call<DetailsMovieResponse>,
                response: Response<DetailsMovieResponse>
            ) {
                when (response.code()) {
                    //Successful responses
                    in 200..299 -> {
                        val movieDetails = response.body()
                        callback(movieDetails)
                    }
                    //Redirection messages
                    in 300..399 -> {
                        errorCallback("Redirection messages : ${response.code()}")
                    }
                    //Client error responses
                    in 400..499 -> {
                        errorCallback("Client error responses : ${response.code()}")
                    }
                    //Server error responses
                    in 500..599 -> {
                        errorCallback("Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailsMovieResponse>, t: Throwable) {
                errorCallback("Error : ${t.message}")
            }

        })
    }
}