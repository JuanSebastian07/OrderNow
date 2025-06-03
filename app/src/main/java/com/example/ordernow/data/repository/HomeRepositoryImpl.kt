package com.example.ordernow.data.repository

import android.util.Log
import com.example.ordernow.domain.model.Banner
import com.example.ordernow.domain.model.Category
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.domain.repository.HomeRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val databaseRealTime: FirebaseDatabase
) : HomeRepository {

    override fun getBannerFlow(): Flow<List<Banner>> = callbackFlow {
        val bannersRef = databaseRealTime.reference.child("Banners")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Banner>()
                for (item in snapshot.children) {
                    val banner = item.getValue(Banner::class.java)
                    banner?.let {
                        list.add(it)
                    }
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        bannersRef.addValueEventListener(listener)

        // Eliminar el listener cuando se cancela el flujo
        awaitClose {
            bannersRef.removeEventListener(listener)
        }
    }

    override fun getCategoryFlow(): Flow<List<Category>> = callbackFlow {
        val categoriesRef = databaseRealTime.reference.child("Category")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Category>()
                for (item in snapshot.children) {
                    val category = item.getValue(Category::class.java)
                    category?.let {
                        list.add(it)
                    }
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        categoriesRef.addValueEventListener(listener)

        awaitClose {
            categoriesRef.removeEventListener(listener)
        }
    }

    override fun getRestaurantsFlow(startAfter: String?, pageSize: Int): Flow<List<ItemsRestaurant>> = callbackFlow {
        val restaurantsRef = databaseRealTime.reference.child("Restaurants")

        // Construir la consulta con paginación
        val query = if (startAfter != null) {
            // Necesitamos asegurarnos de que el startAfterKey es válido
            restaurantsRef.orderByKey()
                .startAfter(startAfter)
                .limitToFirst(pageSize)
        } else {
            restaurantsRef.orderByKey()
                .limitToFirst(pageSize)
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ItemsRestaurant>()
                for (item in snapshot.children) {
                    val itemsRestaurant = item.getValue(ItemsRestaurant::class.java)

                    // IMPORTANTE: Asegurarse de asignar el key de Firebase como id
                    itemsRestaurant?.Id = item.key

                    // Añade debug para verificar los IDs
                    Log.d("HomeRepository", "Restaurant ID: ${item.key}")

                    itemsRestaurant?.let {
                        list.add(it)
                    }
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        query.addValueEventListener(listener)

        awaitClose {
            query.removeEventListener(listener)
        }
    }
}