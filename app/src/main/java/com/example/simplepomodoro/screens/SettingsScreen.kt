package com.example.simplepomodoro.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border // For the border modifier
import androidx.compose.foundation.shape.RoundedCornerShape // For rounded corners
import androidx.compose.material3.Button // For the Button composable
import androidx.compose.material3.ButtonDefaults // For customizing button colors
import androidx.compose.material3.Text // For adding text inside the button


@Composable
fun SettingsScreen(initialFocusDuration: Int, initialBreakDuration: Int, onBack: (Int, Int) -> Unit) {
    // Store the slider values as Float
    var newFocusDuration by remember { mutableFloatStateOf(initialFocusDuration.toFloat()) }
    var newBreakDuration by remember { mutableFloatStateOf(initialBreakDuration.toFloat()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Apply background color from theme
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Focus Timer Section
            Text(
                text = "Focus Timer: ${newFocusDuration.toInt()} minutes",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                value = newFocusDuration,
                onValueChange = { newFocusDuration = it },
                valueRange = 1f..60f, // Allow duration between 1 and 60 minutes
                steps = 0, // No discrete steps
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.onBackground,
                    activeTrackColor = MaterialTheme.colorScheme.onBackground,
                    inactiveTrackColor = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Break Timer Section
            Text(
                text = "Break Timer: ${newBreakDuration.toInt()} minutes",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                value = newBreakDuration,
                onValueChange = { newBreakDuration = it },
                valueRange = 1f..30f, // Allow duration between 1 and 30 minutes
                steps = 0, // No discrete steps
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.onBackground,
                    activeTrackColor = MaterialTheme.colorScheme.onBackground,
                    inactiveTrackColor = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Button to save and go back, passing the new durations as Ints
        Button(
            onClick = { onBack(newFocusDuration.toInt(), newBreakDuration.toInt()) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background, // Button background color
            ),
            shape = RoundedCornerShape(12.dp), // Rounded corners for the button
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(12.dp))
                .align(Alignment.BottomCenter) // Align button at the bottom center
                .padding(10.dp)
        ) {
            Text(
                text = "Save and Go Back",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(
        initialFocusDuration = 25,
        initialBreakDuration = 5,
        onBack = { focusDuration, breakDuration ->
            // Here you can log or just show the values as an example
            println("Focus Duration: $focusDuration, Break Duration: $breakDuration")
        }
    )
}