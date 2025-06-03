package com.example.ordernow.presentation.food_detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordernow.R
import com.example.ordernow.domain.model.ItemsRestaurant

@Composable
fun RowDetail(item : ItemsRestaurant, modifier: Modifier){
    Row (
        modifier = modifier
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Timer,
            contentDescription = null,
            tint = Color.Black
        )
        Text(
            "${item.TimeValue} min",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(R.color.black)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color.Black
        )
        Text(
            "${item.Star}",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(R.color.black)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Icon(
            imageVector = Icons.Filled.Bolt,
            contentDescription = null,
            tint = Color.Black
        )
        Text(
            "${item.Calorie}",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(R.color.black)
        )

    }
}