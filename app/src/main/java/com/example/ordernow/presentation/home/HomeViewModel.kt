package com.example.ordernow.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordernow.core.UiState
import com.example.ordernow.domain.use_case.loadBanner.LoadBannerUseCase
import com.example.ordernow.domain.use_case.loadCategory.LoadCategoryUseCase
import com.example.ordernow.domain.use_case.loadRestaurants.LoadRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadBannerUseCase: LoadBannerUseCase,
    private val loadCategoryUseCase: LoadCategoryUseCase,
    private val loadRestaurantsUseCase: LoadRestaurantsUseCase,
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        loadBanner()
        loadCategory()
        loadInitialRestaurants()
    }

    private fun loadBanner() {
        loadBannerUseCase().onEach { result ->
            when (result) {
                is UiState.Success -> {
                    _state.value = _state.value.copy(
                        banners = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is UiState.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is UiState.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error desconocido",
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadCategory() {
        loadCategoryUseCase().onEach { result ->
            when (result) {
                is UiState.Success -> {
                    _state.value = _state.value.copy(
                        categories = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is UiState.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is UiState.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error desconocido",
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadInitialRestaurants() {
        loadRestaurantsUseCase(pageSize = _state.value.pageSize).onEach { result ->
            when (result) {
                is UiState.Success -> {
                    val data = result.data ?: emptyList()
                    val lastKey = if (data.isNotEmpty()) data.last().Id else null
                    _state.value = _state.value.copy(
                        restaurants = data,
                        isLoading = false,
                        lastRestaurantKey = lastKey,
                        canLoadMoreRestaurants = data.size >= _state.value.pageSize,
                        currentPage = 1,
                        error = null
                    )
                }

                is UiState.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is UiState.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error desconocido",
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadMoreRestaurants() {
        if (_state.value.isLoadingMoreRestaurants || !_state.value.canLoadMoreRestaurants) {
            return
        }

        _state.value = _state.value.copy(isLoadingMoreRestaurants = true)

        // Asegurarnos de que tenemos un lastKey válido
        val lastKey = _state.value.lastRestaurantKey
        if (lastKey == null) {
            _state.value = _state.value.copy(
                isLoadingMoreRestaurants = false,
                canLoadMoreRestaurants = false
            )
            return
        }

        Log.d("HomeViewModel", "Loading more restaurants after key: $lastKey")

        loadRestaurantsUseCase(
            startAfter = lastKey,
            pageSize = _state.value.pageSize
        ).onEach { result ->
            when (result) {
                is UiState.Success -> {
                    val newData = result.data ?: emptyList()

                    // Importante: Verifica que no haya duplicados
                    val uniqueNewData = newData.filter { newRestaurant ->
                        _state.value.restaurants.none { it.Id == newRestaurant.Id }
                    }

                    val lastNewKey = if (uniqueNewData.isNotEmpty()) uniqueNewData.last().Id else lastKey

                    _state.value = _state.value.copy(
                        restaurants = _state.value.restaurants + uniqueNewData,
                        isLoadingMoreRestaurants = false,
                        lastRestaurantKey = lastNewKey,
                        canLoadMoreRestaurants = uniqueNewData.size >= _state.value.pageSize,
                        currentPage = _state.value.currentPage + 1,
                        error = null
                    )

                    Log.d("HomeViewModel", "Added ${uniqueNewData.size} new restaurants, new last key: $lastNewKey")
                }

                is UiState.Loading -> {
                    // Ya establecimos isLoadingMoreRestaurants = true arriba
                }

                is UiState.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error desconocido",
                        isLoadingMoreRestaurants = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}