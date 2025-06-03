package com.example.ordernow.domain.repository

import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    fun toggleFavorite(restaurantId: String, isFavorite: Boolean): Flow<Boolean>
}