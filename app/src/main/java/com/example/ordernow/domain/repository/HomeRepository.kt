package com.example.ordernow.domain.repository

import com.example.ordernow.domain.model.Banner
import com.example.ordernow.domain.model.Category
import com.example.ordernow.domain.model.ItemsRestaurant
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getBannerFlow(): Flow<List<Banner>>
    fun getCategoryFlow(): Flow<List<Category>>
    fun getRestaurantsFlow( startAfter : String? = null, pageSize: Int = 4): Flow<List<ItemsRestaurant>>
}