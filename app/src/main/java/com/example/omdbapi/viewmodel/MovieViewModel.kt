package com.example.omdbapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.omdbapi.lists.MovieList
import com.example.omdbapi.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel():ViewModel() {

    private var movieDetailsLiveData = MutableLiveData<MovieList>()

    fun getMovieDetail(id:String){
        RetrofitInstance.api.getMovie(id,"e3df4631").enqueue(object : Callback<MovieList>{
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.body()!!.Response!="False"){
                    movieDetailsLiveData.value = response.body()
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.d("MovieActivity",t.message.toString())
            }
        })
    }
    fun observerMovieDetails():LiveData<MovieList>{
        return movieDetailsLiveData
    }
}