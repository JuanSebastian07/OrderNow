package com.example.ordernow.domain.use_case.loadRestaurants

import com.example.ordernow.core.UiState
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.domain.repository.HomeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

// Clase de pruebas para el caso de uso que carga restaurantes
class LoadRestaurantsUseCaseTest {
    // Crea un mock "relajado" del repositorio (no lanzará excepciones para métodos no especificados)
    @RelaxedMockK
    private lateinit var homeRepository: HomeRepository

    // Declaración de la clase que vamos a probar
    private lateinit var loadRestaurantsUseCase: LoadRestaurantsUseCase

    // Método que se ejecuta antes de cada prueba
    @Before
    fun onBefore() {
        // Inicializa los mocks anotados con MockK
        MockKAnnotations.init(this)
        // Crea una instancia del caso de uso con el repositorio mockeado
        loadRestaurantsUseCase = LoadRestaurantsUseCase(homeRepository)
    }

    // Prueba que verifica el comportamiento cuando la base de datos retorna una lista vacía
    @Test
    fun `when database returns empty list then return Success with empty list`() = runBlocking {
        // Given: Configuración del escenario de prueba
        // Crea un flujo que emite una lista vacía
        val emptyFlow = flowOf(emptyList<ItemsRestaurant>())
        // Configura el mock para que devuelva la lista vacía cuando se llame al método
        coEvery { homeRepository.getRestaurantsFlow(null, 4) } returns emptyFlow

        // When: Ejecución de la acción a probar
        // Llama al caso de uso con los parámetros especificados
        val resultFlow = loadRestaurantsUseCase(null, 4)
        // Obtiene el primer estado emitido (debería ser un estado de carga)
        val loadingState = resultFlow.first()
        // Obtiene el segundo estado emitido (debería ser un estado de éxito)
        val successState = resultFlow.drop(1).first()

        // Then: Verificación de los resultados esperados
        // Verifica que el método del repositorio se llamó exactamente una vez
        coVerify(exactly = 1) { homeRepository.getRestaurantsFlow(null, 4) }

        // Verifica que el primer estado es de tipo Loading
        assertTrue(loadingState is UiState.Loading)

        // Verifica que el segundo estado es de tipo Success
        assertTrue(successState is UiState.Success<*>)
        // Hace un cast al tipo correcto para poder acceder a los datos
        val successResult = successState as UiState.Success<List<ItemsRestaurant>>
        // Verifica que la lista de datos está vacía
        assertTrue(successResult.data?.isEmpty() ?: false)
    }

    // Prueba que verifica el comportamiento cuando la base de datos retorna restaurantes
    @Test
    fun `when database returns restaurants then return Success with restaurants`() = runBlocking {
        // Given: Configuración del escenario de prueba
        // Crea una lista con dos restaurantes de ejemplo
        val restaurants = listOf(
            ItemsRestaurant(Id = "1", Title = "Restaurant 1", ImagePath = "url1"),
            ItemsRestaurant(Id = "2", Title = "Restaurant 2", ImagePath = "url2")
        )
        // Configura el mock para que devuelva la lista de restaurantes
        coEvery { homeRepository.getRestaurantsFlow(null, 4) } returns flowOf(restaurants)

        // When: Ejecución de la acción a probar
        // Llama al caso de uso
        val resultFlow = loadRestaurantsUseCase(null, 4)
        // Ignora el estado de carga y obtiene directamente el estado de éxito
        val successState = resultFlow.drop(1).first()

        // Then: Verificación de los resultados esperados
        // Verifica que el método del repositorio se llamó exactamente una vez
        coVerify(exactly = 1) { homeRepository.getRestaurantsFlow(null, 4) }
        // Verifica que el estado es de tipo Success
        assertTrue(successState is UiState.Success<*>)
        // Hace un cast al tipo correcto
        val successResult = successState as UiState.Success<List<ItemsRestaurant>>
        // Verifica que la lista tiene 2 elementos
        assertEquals(2, successResult.data?.size)
        // Verifica que el título del primer restaurante es el esperado
        assertEquals("Restaurant 1", successResult.data?.get(0)?.Title)
    }

    // Prueba que verifica el comportamiento cuando la base de datos lanza una excepción
    @Test
    fun `when database throws exception then return Error state`() = runBlocking {
        // Given: Configuración del escenario de prueba
        // Define un mensaje de error
        val errorMessage = "Database error"
        // Configura el mock para que lance una excepción cuando se llame al método
        coEvery { homeRepository.getRestaurantsFlow(null, 4) } returns flow {
            throw Exception(errorMessage)
        }

        // When: Ejecución de la acción a probar
        // Llama al caso de uso
        val resultFlow = loadRestaurantsUseCase(null, 4)
        // Obtiene el estado de carga
        val loadingState = resultFlow.first()
        // Obtiene el estado de error
        val errorState = resultFlow.drop(1).first()

        // Then: Verificación de los resultados esperados
        // Verifica que el método del repositorio se llamó exactamente una vez
        coVerify(exactly = 1) { homeRepository.getRestaurantsFlow(null, 4) }

        // Verifica que el primer estado es de tipo Loading
        assertTrue(loadingState is UiState.Loading)

        // Verifica que el segundo estado es de tipo Error
        assertTrue(errorState is UiState.Error<*>)
        // Hace un cast al tipo correcto
        val errorResult = errorState as UiState.Error<List<ItemsRestaurant>>
        // Verifica que el mensaje de error coincide con el especificado
        assertEquals(errorMessage, errorResult.message)
    }

    // Prueba que verifica que los parámetros personalizados se pasan correctamente al repositorio
    @Test
    fun `when calling use case with custom parameters then pass parameters to repository`() = runBlocking {
        // Given: Configuración del escenario de prueba
        // Define un ID para paginación
        val startAfter = "lastRestaurantId"
        // Define un tamaño de página personalizado
        val pageSize = 10
        // Configura el mock para responder a estos parámetros específicos
        coEvery { homeRepository.getRestaurantsFlow(startAfter, pageSize) } returns flowOf(emptyList())

        // When: Ejecución de la acción a probar
        // Llama al caso de uso con los parámetros personalizados
        loadRestaurantsUseCase(startAfter, pageSize).first()

        // Then: Verificación de los resultados esperados
        // Verifica que el método del repositorio se llamó con los parámetros correctos
        coVerify(exactly = 1) { homeRepository.getRestaurantsFlow(startAfter, pageSize) }
    }
}