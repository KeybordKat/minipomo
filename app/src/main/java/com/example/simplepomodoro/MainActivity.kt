package com.example.simplepomodoro

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.simplepomodoro.navigation.AppNavigator
import com.example.simplepomodoro.ui.theme.SimplePomodoroTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimplePomodoroTheme {
                PermissionHandler()
            }
        }
    }

    // Register a launcher for the permission request
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    // Function to request permission
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Request permission using ActivityResultContracts API
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // PermissionHandler Composable to handle permission logic and navigation
    @Composable
    fun PermissionHandler() {
        val navController = rememberNavController() // This creates a NavHostController

        // Request permission when the Composable is first launched
        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestNotificationPermission()
                }
            }

            // Navigate to Greeting screen first, then to MainContentScreen after the delay
            navController.navigate("greeting") // Start with the Greeting Screen
        }

        // Call the AppNavigator for navigation
        AppNavigator(navController = navController) // Pass the NavHostController
    }
}
