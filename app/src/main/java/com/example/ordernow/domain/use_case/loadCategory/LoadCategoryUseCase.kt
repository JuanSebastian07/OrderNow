package com.example.ordernow.domain.use_case.loadCategory

import com.example.ordernow.core.UiState
import com.example.ordernow.domain.model.Category
import com.example.ordernow.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LoadCategoryUseCase @Inject constructor(
    private val repository: HomeRepository
){
    operator fun invoke(): Flow<UiState<List<Category>>> = repository.getCategoryFlow()
        .map { categories -> UiState.Success(categories) as UiState<List<Category>> }
        .onStart { emit(UiState.Loading()) }
        .catch { e -> emit(UiState.Error(e.message.toString())) }
}