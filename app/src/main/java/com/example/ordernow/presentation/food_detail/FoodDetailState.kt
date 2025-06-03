package com.example.ordernow.presentation.food_detail

data class FoodDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: String? = null
)