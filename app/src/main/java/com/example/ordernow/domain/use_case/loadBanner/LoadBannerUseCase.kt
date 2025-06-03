package com.example.ordernow.domain.use_case.loadBanner

import com.example.ordernow.core.UiState
import com.example.ordernow.domain.model.Banner
import com.example.ordernow.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LoadBannerUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<UiState<List<Banner>>> = repository.getBannerFlow()
        .map { banners -> UiState.Success(banners) as UiState<List<Banner>> }
        .onStart { emit(UiState.Loading()) }
        .catch { e -> emit(UiState.Error(e.message.toString())) }
}