package com.example.ordernow.presentation.food_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ordernow.R
import com.example.ordernow.domain.model.ItemsRestaurant

@Composable
fun FooterSection(itemRestaurant: ItemsRestaurant, modifier: Modifier, quantity: Int) {
    ConstraintLayout (
        modifier = modifier
            .height(75.dp)
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp)
    ){
        val (orderBtn, price ) = createRefs()

        Button(
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.orange)
            ),
            modifier = Modifier
                .width(140.dp)
                .height(50.dp)
                .constrainAs(orderBtn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier=Modifier.width(16.dp))
            Text(
                "Agregar",
                color = Color.White
                )
        }

        Column (
            modifier = Modifier
                .width(140.dp)
                .height(50.dp)
                .constrainAs(price){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            Text(
                "Precio Total",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Text(
                "$${ itemRestaurant.Price * quantity }",
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}