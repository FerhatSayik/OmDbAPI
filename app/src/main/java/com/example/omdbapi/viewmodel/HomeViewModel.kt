package com.example.omdbapi.viewmodel

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.omdbapi.lists.Search
import com.example.omdbapi.lists.SearchList
import com.example.omdbapi.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var searchLiveData = MutableLiveData<List<Search>>()
    companion object{
        var RESPONSE ="com.example.omdbapi.viewmodel.viewmodel"
    }


    fun getSearh(s:String){
        RetrofitInstance.api.getSearch(s,"e3df4631").enqueue(object : Callback<SearchList>{
            override fun onResponse(call: Call<SearchList>, response: Response<SearchList>) {
                if (response.body()!!.Response!="False"){
                    searchLiveData.value = response.body()!!.Search
                }else{
                    HomeViewModel.RESPONSE="False"
                    return
                }
            }

            override fun onFailure(call: Call<SearchList>, t: Throwable) {
                Log.d("HomeActivity",t.message.toString())
            }

        })
    }
    fun observerSearchLiveData():LiveData<List<Search>>{
        return searchLiveData
    }


}