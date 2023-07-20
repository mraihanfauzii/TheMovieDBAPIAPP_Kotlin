package com.example.mrfmovie.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrfmovie.adapter.MovieAdapter
import com.example.mrfmovie.databinding.FragmentHomeBinding
import com.example.mrfmovie.viewmodel.MoviesViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel: MoviesViewModel
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        binding?.apply {
            viewModel.getPopularMovies({ movieList ->
                movieList?.let {
                    movieAdapter.differ.submitList(it)
                    RecyclerViewMovie.apply {
                        layoutManager = LinearLayoutManager(this.context)
                        adapter = movieAdapter
                    }
                }
            }, { errorMessage ->
                Log.e("Response Code", errorMessage)
            })
        }

        return binding.root
    }

}