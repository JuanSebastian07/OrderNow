package com.example.ordernow.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ItemsRestaurant(
    var BestFood: Boolean = false,
    val Calorie: Int = 0,
    val CategoryId: String = "",
    val Description: String = "",
    var Id: String? = null,
    val ImagePath: String = "",
    val LocationId: Int = 0,
    val Price: Double = 0.0,
    val PriceId: Int = 0,
    val Star: Double = 0.0,
    val TimeId: Int = 0,
    val TimeValue: Int = 0,
    val Title: String = ""
) : Parcelable

