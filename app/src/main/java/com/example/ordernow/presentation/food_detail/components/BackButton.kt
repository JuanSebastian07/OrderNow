package com.example.ordernow.presentation.food_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(modifier: Modifier, navigateBack: () -> Unit){
    Box(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp)
            .clip(RoundedCornerShape(48.dp))
            .size(48.dp)
            .background(color = Color.DarkGray, shape = CircleShape)
            .clickable {
                navigateBack()
            },
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.White
        )
    }
}