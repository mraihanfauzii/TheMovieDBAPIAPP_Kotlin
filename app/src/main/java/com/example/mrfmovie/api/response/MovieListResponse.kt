package com.example.mrfmovie.api.response

import com.example.mrfmovie.model.Result

data class MovieListResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)