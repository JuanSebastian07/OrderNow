package com.example.ordernow.domain.use_case.toggleFavorite

import com.example.ordernow.core.UiState
import com.example.ordernow.domain.repository.DetailRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository : DetailRepository
) {
    operator fun invoke(id: String, isFavorite: Boolean) = repository.toggleFavorite(id, isFavorite)
        .map { isFavorite -> UiState.Success(isFavorite) as UiState<Boolean> }
        .onStart { emit(UiState.Loading()) }
        .catch { e -> emit(UiState.Error(e.message.toString())) }
}