package com.example.omdbapi.lists

data class SearchList(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)