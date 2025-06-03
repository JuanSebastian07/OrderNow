package com.example.ordernow.presentation.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ordernow.presentation.activity.components.ContentScreen
import com.example.ordernow.presentation.activity.components.MyBottomBar
import com.example.ordernow.presentation.sign_in.GoogleAuthClient
import com.example.ordernow.presentation.util.NavItem

@Composable
fun MainScreen(
    googleAuthClient: GoogleAuthClient,
    navController: NavHostController,
) {
    val navItemList = listOf(
        NavItem(label = "Home", icon = Icons.Default.Home),
        NavItem(label = "Notification", icon = Icons.Default.Notifications),
        NavItem(label = "Settings", icon = Icons.Default.Settings),
        NavItem(label = "Perfil", icon = Icons.Default.Person)
    )

    // El estado se eleva a este nivel
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
            ) {
                // Pasamos el estado y una función para actualizarlo
                MyBottomBar(
                    navItemList = navItemList,
                    selectedIndex = selectedIndex,
                    onSelectedIndexChange = { selectedIndex = it }
                )
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            googleAuthClient = googleAuthClient,
            navController = navController
        )
    }

}
