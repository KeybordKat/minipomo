package com.example.simplepomodoro.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.simplepomodoro.screens.GreetingScreen
import com.example.simplepomodoro.screens.MainContentScreen
import com.example.simplepomodoro.screens.SettingsScreen

@Composable
fun AppNavigator(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = "greeting"
        ) {
            composable("greeting") {
                GreetingScreen(
                    onNavigateToMainContent = {
                        // This allows the app to navigate manually to the main content screen
                        navController.navigate("mainContent/25/5") // Default focus and break duration
                    }
                )
            }

            composable(
                route = "mainContent/{focusDuration}/{breakDuration}",
                arguments = listOf(
                    navArgument("focusDuration") { type = NavType.IntType },
                    navArgument("breakDuration") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val focusDuration = backStackEntry.arguments?.getInt("focusDuration") ?: 25
                val breakDuration = backStackEntry.arguments?.getInt("breakDuration") ?: 5

                MainContentScreen(
                    focusDuration = focusDuration,
                    breakDuration = breakDuration,
                    onSettingsClick = {
                        navController.navigate("settings/$focusDuration/$breakDuration")
                    }
                )
            }

            composable(
                route = "settings/{currentFocusDuration}/{currentBreakDuration}",
                arguments = listOf(
                    navArgument("currentFocusDuration") { type = NavType.IntType },
                    navArgument("currentBreakDuration") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val currentFocusDuration = backStackEntry.arguments?.getInt("currentFocusDuration") ?: 25
                val currentBreakDuration = backStackEntry.arguments?.getInt("currentBreakDuration") ?: 5

                SettingsScreen(
                    initialFocusDuration = currentFocusDuration,
                    initialBreakDuration = currentBreakDuration,
                    onBack = { focusDuration, breakDuration ->
                        navController.navigate("mainContent/$focusDuration/$breakDuration") {
                            popUpTo("mainContent/{focusDuration}/{breakDuration}") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
