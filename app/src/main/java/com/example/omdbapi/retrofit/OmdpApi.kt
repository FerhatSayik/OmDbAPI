package com.example.omdbapi.retrofit

import com.example.omdbapi.lists.MovieList
import com.example.omdbapi.lists.SearchList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdpApi {

    @GET("?")
    fun getSearch(@Query("s") Title:String,@Query("apikey") apikey:String): Call<SearchList>

    @GET("?")
    fun getMovie(@Query("i") Title:String,@Query("apikey") apikey:String): Call<MovieList>

}