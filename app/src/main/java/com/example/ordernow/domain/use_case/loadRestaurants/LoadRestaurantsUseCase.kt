package com.example.ordernow.domain.use_case.loadRestaurants

import com.example.ordernow.core.UiState
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LoadRestaurantsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke( startAfter : String? = null, pageSize: Int = 4 ): Flow<UiState<List<ItemsRestaurant>>> = repository.getRestaurantsFlow( startAfter, pageSize )
        .map { restaurants -> UiState.Success(restaurants) as UiState<List<ItemsRestaurant>> }
        .onStart { emit(UiState.Loading()) }
        .catch { e -> emit(UiState.Error(e.message.toString())) }
}