package com.example.simplepomodoro.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.animation.core.tween
import kotlinx.coroutines.delay

@Composable
fun GreetingScreen(onNavigateToMainContent: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    // Trigger the transition after a brief delay
    LaunchedEffect(Unit) {
        delay(1500) // Wait for 1.5 seconds before starting the fade-out
        isVisible = false
        delay(500) // Allow fade-out animation to complete
        onNavigateToMainContent() // Navigate to the main content screen
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Apply background color from theme
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(durationMillis = 900, delayMillis = 100))
        ) {
            Text(
                text = "Welcome Back",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground // Text color based on theme
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    GreetingScreen(onNavigateToMainContent = {})
}
