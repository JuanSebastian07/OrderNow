package com.example.ordernow.data.repository

import android.util.Log
import com.example.ordernow.domain.repository.DetailRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DetailRepositoryImpl @Inject constructor(
    private val databaseRealTime: FirebaseDatabase
) : DetailRepository {

    override fun toggleFavorite(restaurantId: String, isFavorite: Boolean): Flow<Boolean> {
        return callbackFlow {
            val restaurantsRef = databaseRealTime.reference.child("Restaurants")
            val restaurantRef = restaurantsRef.child(restaurantId)

            restaurantRef.child("BestFood").setValue(isFavorite)
                .addOnSuccessListener {
                    trySend(isFavorite)
                    close()
                }
                .addOnFailureListener { exception ->
                    close(exception)
                }

            // Importante: Necesitamos esta línea para asegurar que el canal se cierra si el flujo es cancelado
            awaitClose { }
        }
    }
}