package com.example.ordernow.presentation.food_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordernow.core.UiState
import com.example.ordernow.domain.use_case.toggleFavorite.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    private val _state = mutableStateOf(FoodDetailState())
    val state: State<FoodDetailState> = _state

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(id, isFavorite).collect { result ->
                when (result) {
                    is UiState.Success -> {
                        _state.value = _state.value.copy(
                            isFavorite = result.data ?: false,
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
                        Log.e("FoodDetailViewModel", "Error: ${result.message}")
                    }
                }
            }
        }
    }
}