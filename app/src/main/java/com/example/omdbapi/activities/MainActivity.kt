package com.example.omdbapi.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbapi.R
import com.example.omdbapi.adapter.SearchAdapter
import com.example.omdbapi.databinding.ActivityMainBinding
import com.example.omdbapi.viewmodel.HomeViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var searchAdapter:SearchAdapter
    lateinit var edtText:EditText


    companion object{
        const val MOVIE_ID ="com.example.omdbapi.activities.id"
        const val MOVIE_NAME ="com.example.omdbapi.activities.name"
        const val MOVIE_THUMB ="com.example.omdbapi.activities.thumb"
        const val CATEGORY_NAME ="com.example.omdbapi.activities.category"
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder = AlertDialog.Builder(this)

        homeMvvm =  ViewModelProviders.of(this)[HomeViewModel::class.java]
        searchAdapter = SearchAdapter()

        prepareSearchItemsRecyclerView()
        edtText = findViewById(R.id.tv_serach)
        val progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setCancelable(false)

        binding.btnSearch.setOnClickListener {
            progress.show()
            homeMvvm.getSearh(edtText.text.toString())
            if (HomeViewModel.RESPONSE == "False"){

                builder.setTitle("Warning!")
                builder.setMessage("Movie Not Found")
                builder.setNeutralButton("Cancel"){dialogInterface , which ->

                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }else{
                observerSearchLiveData()
            }

            hideSoftKeyboard(this)
            progress.dismiss()
        }

        onItemClick()

    }

    private fun onItemClick() {
        searchAdapter.onItemClick = {search ->
            val intent = Intent(this,MovieActivity::class.java)
            intent.putExtra(MainActivity.MOVIE_ID,search.imdbID)
            intent.putExtra(MainActivity.MOVIE_NAME,search.Title)
            startActivity(intent)
        }
    }

    private fun prepareSearchItemsRecyclerView() {
        binding.recSearchList.apply { layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = searchAdapter
        }
    }

    private fun observerSearchLiveData() {
        homeMvvm.observerSearchLiveData().observe(this, Observer { Search ->
            searchAdapter.setSearchList(Search)
        })
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

}