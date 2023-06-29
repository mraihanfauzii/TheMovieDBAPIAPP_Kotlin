package com.example.mrfmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.example.mrfmovie.api.ApiClient
import com.example.mrfmovie.api.ApiService
import com.example.mrfmovie.databinding.ActivityDetailsMovieBinding
import com.example.mrfmovie.response.DetailsMovieResponse
import com.example.mrfmovie.utils.Constants.POSTER_BASEURL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMovieBinding
    private val api : ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId = intent.getIntExtra("id", 1)
        binding.apply {
            val callDetailsMovieApi = api.getMovieDetails(movieId)
            callDetailsMovieApi.enqueue(object : Callback<DetailsMovieResponse>{
                override fun onResponse(
                    call: Call<DetailsMovieResponse>,
                    response: Response<DetailsMovieResponse>
                ) {
                    when(response.code()){
                        //Successful responses
                        in 200..299->{
                            response.body().let { itBody->
                                val imagePoster = POSTER_BASEURL + itBody!!.poster_path
                                imgMovie.load(imagePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgBackground.load(imagePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                tvMovieName.text = itBody.title
                                tvTagLine.text = itBody.tagline
                                tvMovieReleasedDate.text = itBody.release_date
                                tvMovieRating.text = itBody.vote_average.toString()
                                tvMovieRuntime.text = itBody.runtime.toString()
                                tvMovieBudget.text = itBody.budget.toString()
                                tvMovieRevenue.text = itBody.revenue.toString()
                                tvMovieOverview.text = itBody.overview
                            }
                        }
                        //Redirection messages
                        in 300..399->{
                            Log.d("Response Code", "Redirection messages : ${response.code()}")
                        }
                        //Client error responses
                        in 400..499->{
                            Log.d("Response Code", "Client error responses : ${response.code()}")
                        }
                        //Server error responses
                        in 500..599->{
                            Log.d("Response Code", "Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<DetailsMovieResponse>, t: Throwable) {
                    Log.e("OnFailure", "Err : ${t.message}")
                }

            })
        }
    }
}