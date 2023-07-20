package com.example.mrfmovie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.size.Scale
import com.example.mrfmovie.R
import com.example.mrfmovie.api.ApiClient
import com.example.mrfmovie.api.ApiService
import com.example.mrfmovie.databinding.ActivityDetailsMovieBinding
import com.example.mrfmovie.api.response.DetailsMovieResponse
import com.example.mrfmovie.utils.Constants.POSTER_BASEURL
import com.example.mrfmovie.viewmodel.MoviesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMovieBinding
    private lateinit var viewModel: MoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("id", 1)

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetails(movieId,
            { movieDetails ->
                binding.apply {
                    movieDetails?.let { details ->
                        val imagePoster = POSTER_BASEURL + details.poster_path
                        imgMovie.load(imagePoster) {
                            crossfade(true)
                            placeholder(R.drawable.poster_placeholder)
                            scale(Scale.FILL)
                        }
                        imgBackground.load(imagePoster) {
                            crossfade(true)
                            placeholder(R.drawable.poster_placeholder)
                            scale(Scale.FILL)
                        }
                        tvMovieName.text = details.title
                        tvTagLine.text = details.tagline
                        tvMovieReleasedDate.text = details.release_date
                        tvMovieRating.text = details.vote_average.toString()
                        tvMovieRuntime.text = details.runtime.toString()
                        tvMovieBudget.text = details.budget.toString()
                        tvMovieRevenue.text = details.revenue.toString()
                        tvMovieOverview.text = details.overview
                    }
                }
            }, { errorMessage ->
                Log.e("Response Code", errorMessage)
            })
    }
}