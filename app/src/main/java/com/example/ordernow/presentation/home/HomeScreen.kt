package com.example.ordernow.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ordernow.presentation.home.components.BannerSection
import com.example.ordernow.presentation.home.components.CategorySection
import com.example.ordernow.presentation.home.components.RestaurantItem
import com.example.ordernow.presentation.home.components.SearchBar
import com.example.ordernow.presentation.home.components.ShimmerRestaurantItem
import com.example.ordernow.presentation.util.FoodDetail

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.state.value

    // Para detectar cuando se llega al final de la lista
    val listState = rememberLazyListState()

    // Mejorado el efecto para detectar cuando se llega al final de la lista
    LaunchedEffect(listState) {
        snapshotFlow {
            // Explícitamente calculamos estos valores cada vez
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItemCount = listState.layoutInfo.totalItemsCount

            // Registro de depuración para verificar qué está pasando
            Log.d("ScrollDetection", "Last visible: $lastVisibleItem, Total: $totalItemCount")

            // Verificar si estamos a 2 ítems del final
            lastVisibleItem >= totalItemCount - 2 && totalItemCount > 0
        }.collect { isNearEnd ->
            Log.d("ScrollDetection", "Near end: $isNearEnd, Loading: ${state.isLoadingMoreRestaurants}, Can load more: ${state.canLoadMoreRestaurants}")

            if (isNearEnd && !state.isLoadingMoreRestaurants && state.canLoadMoreRestaurants) {
                Log.d("ScrollDetection", "Llamando a loadMoreRestaurants()")
                viewModel.loadMoreRestaurants()
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // SearchBar fijo en la parte superior
        SearchBar()

        // Contenido scrolleable
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                BannerSection(state)
            }
            item {
                CategorySection(state)
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            }

            item {
                Text(
                    text = "Restaurantes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Si está cargando inicialmente y no hay restaurantes, muestra el shimmer
            if (state.isLoading && state.restaurants.isEmpty()) {
                items(3) {
                    ShimmerRestaurantItem()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                // Cada restaurante es un ítem individual para que el scroll funcione bien
                itemsIndexed(state.restaurants) { index, restaurant ->
                    Log.d("RestaurantItem", "Mostrando restaurante #$index: ${restaurant.Id}")
                    RestaurantItem(
                        restaurant = restaurant,
                        onItemClick = {
                            navController.navigate(FoodDetail(restaurant))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Indicador de carga al final
            item {
                if (state.isLoadingMoreRestaurants) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}