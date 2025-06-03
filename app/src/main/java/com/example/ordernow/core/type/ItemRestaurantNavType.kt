package com.example.ordernow.core.type

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.ordernow.domain.model.ItemsRestaurant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val  itemRestaurantNavType = object : NavType<ItemsRestaurant>(isNullableAllowed = true){

    override fun get(bundle: Bundle, key: String): ItemsRestaurant? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, ItemsRestaurant::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): ItemsRestaurant {
        return Json.decodeFromString<ItemsRestaurant>(value)
    }

    override fun serializeAsValue(value: ItemsRestaurant): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: ItemsRestaurant) {
        bundle.putParcelable(key, value)
    }

}