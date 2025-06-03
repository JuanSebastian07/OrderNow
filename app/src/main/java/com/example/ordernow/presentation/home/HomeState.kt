package com.example.ordernow.presentation.home

import com.example.ordernow.domain.model.Banner
import com.example.ordernow.domain.model.Category
import com.example.ordernow.domain.model.ItemsRestaurant

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val restaurants: List<ItemsRestaurant> = emptyList(),
    val isLoadingMoreRestaurants: Boolean = false,
    val canLoadMoreRestaurants: Boolean = true,
    val currentPage: Int = 0,
    val pageSize: Int = 4,
    val lastRestaurantKey: String? = null,
    val error: String? = null
)
