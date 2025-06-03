package com.example.ordernow.presentation.util

import com.example.ordernow.domain.model.ItemsRestaurant
import kotlinx.serialization.Serializable

@Serializable
data object SignIn

@Serializable
data object Main

@Serializable
data object Profile

@Serializable
data class FoodDetail(val itemRestaurant : ItemsRestaurant)