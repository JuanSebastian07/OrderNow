package com.example.ordernow.presentation.food_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ordernow.domain.model.ItemsRestaurant

@Composable
fun DescriptionSection(itemRestaurant: ItemsRestaurant, modifier: Modifier) {
    Text(
        modifier = modifier
            .padding(top = 32.dp, end = 16.dp, start = 16.dp),
        text = itemRestaurant.Description
    )
}