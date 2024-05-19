package com.example.chatapp.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.model.YourRecentSearch
import com.example.chatapp.model.YourRecommendSearch

class SearchViewModel: ViewModel() {
    var recentSearch by mutableStateOf<List<YourRecentSearch>?>(null)
        private set
    var recommendSearch by mutableStateOf<List<YourRecommendSearch>?>(null)
        private set
    fun fetchYourRecentSearch() {
        val list = listOf(
            YourRecentSearch(R.drawable.newuser, "Mai Thuong"),
            YourRecentSearch(R.drawable.avatar_garena_2, "Tran Dang"),
            YourRecentSearch(R.drawable.newuser, "Wong Da"),
            YourRecentSearch(R.drawable.newuser, "Ton Lu"),
            YourRecentSearch(R.drawable.avatar_garena_2, "Tao La Ai"),
            YourRecentSearch(R.drawable.newuser, "Rang"),
        )
        recentSearch = list
    }

    fun fetchRecommendSearch() {
        val list = listOf(
            YourRecommendSearch(R.drawable.avatar_girl_garena, "Crush"),
            YourRecommendSearch(R.drawable.avatar_garena_2, "Garena"),
            YourRecommendSearch(R.drawable.rangdong, "Ton Lu"),
            YourRecommendSearch(R.drawable.rangdong, "Tao La Ai"),
            YourRecommendSearch(R.drawable.newuser, "Phuc Is Me"),
            YourRecommendSearch(R.drawable.newuser, "EEEEEE"),
            YourRecommendSearch(R.drawable.newuser, "DDDDg")
        )
        recommendSearch = list
    }
}