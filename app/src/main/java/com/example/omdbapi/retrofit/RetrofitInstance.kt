package com.example.omdbapi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    val api:OmdpApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/?")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdpApi::class.java)
    }
}