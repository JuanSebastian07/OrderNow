package com.example.ordernow.presentation.food_detail.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.presentation.food_detail.FoodDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteButton(
    modifier: Modifier,
    itemRestaurant: ItemsRestaurant,
    snackbarHostState: SnackbarHostState,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state
    var isFavorite by remember { mutableStateOf(itemRestaurant.BestFood) }
    val scope = rememberCoroutineScope()


    val scale = animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "favoriteScale"
    )

    val icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    val color = if (isFavorite) Color.Red else Color.White

    Box(
        modifier = modifier
            .padding(end = 16.dp, top = 16.dp)
            .clip(RoundedCornerShape(48.dp))
            .size(48.dp)
            .background(color = Color.DarkGray, shape = CircleShape)
            .clickable {
                isFavorite = !isFavorite
                viewModel.toggleFavorite(itemRestaurant.Id!!, isFavorite)

                scope.launch {
                    val message = if (isFavorite) "Añadido a favoritos" else "Quitado de favoritos"
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isFavorite) "Quitar de favoritos" else "Añadir a favoritos",
            tint = color,
            modifier = Modifier.scale(scale.value)
        )
    }

    LaunchedEffect(state.error) {
        if (state.error?.isNotEmpty() == true) {
            snackbarHostState.showSnackbar(
                message = state.error ?: "Error desconocido",
                duration = SnackbarDuration.Long
            )
        }
    }

}
