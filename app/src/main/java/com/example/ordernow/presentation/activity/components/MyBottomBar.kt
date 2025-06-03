package com.example.ordernow.presentation.activity.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.ordernow.R
import com.example.ordernow.presentation.util.NavItem

@Composable
fun MyBottomBar(
    navItemList: List<NavItem>,
    selectedIndex: Int,                  // Recibimos el estado
    onSelectedIndexChange: (Int) -> Unit // Recibimos función para actualizar
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.orange) // Color naranja similar al de la imagen
        )
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp),
            containerColor = Color.Transparent, // Mantiene transparente para que se vea el color de la Card
            tonalElevation = 0.dp
        ) {
            navItemList.forEachIndexed { index, navItem ->
                NavigationBarItem(
                    modifier = Modifier.padding(top = 16.dp),
                    selected = selectedIndex == index, // Cambia esto según la navegación actual
                    onClick = {
                        onSelectedIndexChange(index) // Usamos la función en lugar de modificar directamente
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.label,
                            tint = Color.White // Color blanco para los iconos
                        )
                    },
                    label = {
                        Text(
                            text = navItem.label,
                            color = Color.White // Color blanco para el texto
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFFF47B50), // Una variante un poco más clara para el indicador
                        unselectedIconColor = Color(0xFFFFDDCC), // Un tono más claro para los no seleccionados
                        unselectedTextColor = Color(0xFFFFDDCC)
                    )
                )
            }
        }
    }
}