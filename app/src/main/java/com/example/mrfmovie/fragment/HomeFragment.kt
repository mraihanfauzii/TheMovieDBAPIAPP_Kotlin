package com.example.mrfmovie.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrfmovie.adapter.MovieAdapter
import com.example.mrfmovie.api.ApiClient
import com.example.mrfmovie.api.ApiService
import com.example.mrfmovie.databinding.FragmentHomeBinding
import com.example.mrfmovie.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val movieAdapter by lazy { MovieAdapter() }
    private val api : ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding?.apply {
        //    ProgressBarMovie.visibility = View.VISIBLE
            val callMovieApi = api.getPopularMovie(1)
            callMovieApi.enqueue(object : Callback<MovieListResponse> {

                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
               //     ProgressBarMovie.visibility=View.GONE
                    when(response.code()){
                        //Successful responses
                        in 200..299->{
                            response.body().let { itBody->
                                itBody?.results.let { itData->
                                    if (itData!!.isNotEmpty()){
                                        movieAdapter.differ.submitList(itData)
                                        RecyclerViewMovie.apply {
                                            layoutManager=LinearLayoutManager(this.context)
                                            adapter=movieAdapter
                                        }
                                    }
                                }
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

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                //    ProgressBarMovie.visibility=View.GONE
                    Log.e("OnFailure", "Err : ${t.message}")
                }

            })
        }
        return binding.root
    }

}