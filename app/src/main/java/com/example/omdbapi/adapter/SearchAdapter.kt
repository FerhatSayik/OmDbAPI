package com.example.omdbapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.omdbapi.databinding.SearchItemBinding
import com.example.omdbapi.lists.Search
import java.util.ArrayList

class SearchAdapter():RecyclerView.Adapter<SearchAdapter.SearchViewModel>() {

    private var searchList = ArrayList<Search>()
    lateinit var onItemClick:((Search)->Unit)

    fun setSearchList(searchListBy: List<Search>){
        this.searchList = searchListBy as ArrayList<Search>
        notifyDataSetChanged()
    }
    class SearchViewModel(val binding: SearchItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewModel {
        return SearchViewModel(SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewModel, position: Int) {
        Glide.with(holder.itemView).load(searchList[position].Poster).into(holder.binding.imgSearch)
        holder.binding.tvMovieName.text = searchList[position].Title
        holder.binding.tvMovieYear.text = "Year: "+searchList[position].Year

        holder.itemView.setOnClickListener {
            onItemClick.invoke(searchList[position])
        }

    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}