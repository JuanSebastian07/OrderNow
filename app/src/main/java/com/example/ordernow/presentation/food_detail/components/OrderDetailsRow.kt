package com.example.ordernow.presentation.food_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ordernow.R
import com.example.ordernow.domain.model.ItemsRestaurant

@Composable
fun OrderDetailsRow(
    itemsRestaurant: ItemsRestaurant,
    modifier: Modifier,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    quantity: Int
){
    ConstraintLayout (
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        val (price, buttons) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .width(100.dp)
                .padding(start = 8.dp)
                .background(
                    shape = RoundedCornerShape(100.dp),
                    color = colorResource(R.color.orange)
                ).constrainAs(buttons){
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            val (plusCartBtn, minusCartBtn, numberItemText) = createRefs()

            Text(
                text = quantity.toString(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .size(28.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .wrapContentSize(Alignment.Center)
                    .constrainAs(numberItemText){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Box (
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .size(28.dp)
                    .constrainAs(plusCartBtn){
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }.clickable {
                        onIncrement()
                    }
            ){
                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            Box (
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .size(28.dp)
                    .constrainAs(minusCartBtn){
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }.clickable {
                        onDecrement()
                    }
            ){
                Text(
                    text = "-",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }

        Text(
            text = "$${itemsRestaurant.Price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(price){
                    end.linkTo(buttons.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

    }
}