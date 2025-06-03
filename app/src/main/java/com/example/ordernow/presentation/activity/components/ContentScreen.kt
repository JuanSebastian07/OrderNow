package com.example.ordernow.presentation.activity.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ordernow.presentation.home.HomeScreen
import com.example.ordernow.presentation.notification.NotificationScreen
import com.example.ordernow.presentation.profile.ProfileScreen
import com.example.ordernow.presentation.setting.SettingScreen
import com.example.ordernow.presentation.sign_in.GoogleAuthClient
import com.example.ordernow.presentation.util.Main
import com.example.ordernow.presentation.util.SignIn
import kotlinx.coroutines.launch

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    googleAuthClient: GoogleAuthClient,
    navController: NavHostController
){
    val scope = rememberCoroutineScope()
    when(selectedIndex){
        0 -> HomeScreen(modifier, navController = navController)
        1 -> NotificationScreen()
        2 -> SettingScreen()
        3 -> ProfileScreen(
            userData = googleAuthClient.getSignedInUser(),
            onSignOut = {
                scope.launch {
                    googleAuthClient.signOut()
                    // Use the passed navController instead of creating a new one
                    navController.navigate(SignIn) {
                        popUpTo(Main) { inclusive = true }  // Changed from Profile to Main
                    }
                }
            }
        )
    }
}