package com.example.ordernow.presentation.food_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.ordernow.R
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.presentation.food_detail.components.BackButton
import com.example.ordernow.presentation.food_detail.components.DescriptionSection
import com.example.ordernow.presentation.food_detail.components.FavoriteButton
import com.example.ordernow.presentation.food_detail.components.FooterSection
import com.example.ordernow.presentation.food_detail.components.OrderDetailsRow
import com.example.ordernow.presentation.food_detail.components.RowDetail

@Composable
fun FoodDetailScreen(
    itemRestaurant: ItemsRestaurant,
    modifier: Modifier,
    navigateBack: () -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (back, fav, mainImage, arcImage, title, detailRow, orderDetailsRow, descriptionSection, footer) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(itemRestaurant.ImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(
                    RoundedCornerShape(
                    bottomStart = 32.dp,bottomEnd = 32.dp
                    )
                )
                .constrainAs(mainImage) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )

        Image(
            painter = painterResource(R.drawable.arc_bg),
            contentDescription = null,
            modifier = Modifier
                .height(190.dp)
                .constrainAs(arcImage) {
                    top.linkTo(mainImage.bottom, margin = (-30).dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )

            BackButton(
                Modifier.constrainAs(back) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                navigateBack = navigateBack
            )

            // Pasa el snackbarHostState al FavoriteButton
            FavoriteButton(
                Modifier.constrainAs(fav) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
                itemRestaurant,
                snackbarHostState = snackbarHostState
            )

            Text(
                text = itemRestaurant.Title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .constrainAs(title) {
                        top.linkTo(arcImage.top, margin = 32.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )

            RowDetail(
                itemRestaurant,
                Modifier.constrainAs(detailRow) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            OrderDetailsRow(
                itemRestaurant,
                Modifier.constrainAs(orderDetailsRow) {
                    top.linkTo(detailRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onIncrement = { quantity++ },
                onDecrement = { if (quantity != 1) quantity-- },
                quantity = quantity
            )

            DescriptionSection(
                itemRestaurant,
                Modifier.constrainAs(descriptionSection) {
                    top.linkTo(orderDetailsRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            FooterSection(
                itemRestaurant,
                Modifier.constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
                quantity = quantity
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 72.dp)
                .align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.orange),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = snackbarData.visuals.message,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = { snackbarData.dismiss() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        )
    }
}