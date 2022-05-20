package com.example.omdbapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.omdbapi.R
import com.example.omdbapi.databinding.ActivityMovieBinding
import com.example.omdbapi.lists.MovieList
import com.example.omdbapi.viewmodel.MovieViewModel

class MovieActivity : AppCompatActivity() {
    private lateinit var movieId:String
    private lateinit var movieName:String
    private lateinit var movieMvvm:MovieViewModel
    private lateinit var binding: ActivityMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //actionbar
        val actionbar = supportActionBar


        getMealInformationFromIntent()
        actionbar!!.title = movieName
        actionbar.setDisplayHomeAsUpEnabled(true)

        movieMvvm =ViewModelProviders.of(this)[MovieViewModel::class.java]
        movieMvvm.getMovieDetail(movieId)
        observerMovieDetailLiveData()



    }

    private fun observerMovieDetailLiveData() {
        movieMvvm.observerMovieDetails().observe(this,object :Observer<MovieList>{
            override fun onChanged(t: MovieList?) {
                Glide.with(this@MovieActivity).load(t?.Poster).into(binding.imgMovie)
                binding.tvCategory.text = "Category: "+t!!.Genre
                binding.tvReleased.text = "Released: "+t!!.Released
                binding.tvInstructions.text = t!!.Plot
            }

        })
    }

    private fun getMealInformationFromIntent() {
        movieId = intent.getStringExtra(MainActivity.MOVIE_ID)!!
        movieName = intent.getStringExtra(MainActivity.MOVIE_NAME)!!
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}