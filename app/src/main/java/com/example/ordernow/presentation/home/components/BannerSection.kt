package com.example.ordernow.presentation.home.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ordernow.domain.model.Banner
import com.example.ordernow.presentation.home.HomeState

@Composable
fun BannerSection(state: HomeState) {

    val context = LocalContext.current

    // Estado para controlar si todas las imágenes están precargadas
    var imagesPreloaded by remember { mutableStateOf(false) }

    // Efecto para precargar imágenes cuando los datos están disponibles
    LaunchedEffect(state.banners) {
        if (!state.isLoading && state.banners.isNotEmpty()) {
            // Reset estado de precarga
            imagesPreloaded = false

            // Iniciar la precarga
            preloadImages(context, state.banners) {
                // Esto se ejecuta cuando todas las imágenes han sido precargadas
                imagesPreloaded = true
            }
        }
    }

    // Mostrar shimmer mientras los datos se cargan o las imágenes se precargan
    if (state.isLoading || !imagesPreloaded) {
        BannerSectionShimmer()
    } else {
        BannerCarousel(banners = state.banners)
    }
}

// Función para precargar imágenes
private fun preloadImages(context: Context, banners: List<Banner>, onAllLoaded: () -> Unit) {
    val totalImages = banners.size
    var loadedCount = 0

    if (totalImages == 0) {
        onAllLoaded()
        return
    }

    banners.forEach { banner ->
        val request = ImageRequest.Builder(context)
            .data(banner.image)
            .listener(
                onSuccess = { _, _ ->
                    loadedCount++
                    if (loadedCount >= totalImages) {
                        onAllLoaded()
                    }
                },
                onError = { _, _ ->
                    loadedCount++
                    if (loadedCount >= totalImages) {
                        onAllLoaded()
                    }
                }
            )
            .build()

        // Enviar la solicitud a la cola del cargador de imágenes
        Coil.imageLoader(context).enqueue(request)
    }
}

@Composable
fun BannerCarousel(banners: List<Banner>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState { banners.size }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            BannerItem(banner = banners[page])
        }

        Row(
            Modifier
                .height(20.dp)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(banners.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Composable
fun BannerItem(banner: Banner) {
    // Ya que las imágenes están precargadas, se mostrarán inmediatamente desde la caché
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(banner.image)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .height(150.dp),
        contentScale = ContentScale.Crop
    )
}