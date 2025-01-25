package com.example.simplepomodoro.screens

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.simplepomodoro.R
import androidx.compose.foundation.layout.Arrangement as Arrangement1
import java.util.Locale // Make sure to import this



fun playTimerEndSound(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.timer_end_sound)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { it.release() }
}

fun showTimerEndNotification(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the notification channel for Android 8.0+ (API level 26)
        val channel = NotificationChannel(
            "timer_channel",
            "Timer Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This is the channel for Pomodoro timer notifications"
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)

        // Log to see if we are creating the notification channel
        Log.d("Notification", "Notification channel created for Android 8.0+")

        val notification = Notification.Builder(context, "timer_channel")
            .setContentTitle("minipomo")
            .setContentText("Time's up! Your timer has ended.")
            .setSmallIcon(R.drawable.timer) // Use your icon here
            .setAutoCancel(true)
            .build()

        // Log to see if notification is being built
        Log.d("Notification", "Notification built for Android 8.0+")

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManagerCompat.notify(1, notification)

        // Log after notifying
        Log.d("Notification", "Notification triggered for Android 8.0+")
    } else {
        val notification = NotificationCompat.Builder(context, "timer_channel")
            .setContentTitle("Pomodoro Timer")
            .setContentText("Time's up! Your session has ended.")
            .setSmallIcon(R.drawable.timer)
            .setAutoCancel(true)
            .build()

        // Log to see if we are creating the notification
        Log.d("Notification", "Notification built for below Android 8.0")

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManagerCompat.notify(1, notification)

        // Log after notifying
        Log.d("Notification", "Notification triggered for below Android 8.0")
    }
}

@Composable
fun MainContentScreen(
    focusDuration: Int,
    breakDuration: Int,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    var timeRemaining by remember { mutableIntStateOf(focusDuration * 60) }
    var isRunning by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    var isOnBreak by remember { mutableStateOf(false) }
    var breakTimeRemaining by remember { mutableIntStateOf(breakDuration * 60) }

    val mainTimer = remember { mutableStateOf<CountDownTimer?>(null) }
    val breakTimer = remember { mutableStateOf<CountDownTimer?>(null) }

    // State for the editable text field
    var taskText by remember { mutableStateOf("") }
    var isEditingTask by remember { mutableStateOf(false) }

    // Local function to start the break timer
    fun startBreakTimer() {
        breakTimer.value = object : CountDownTimer(breakTimeRemaining.toLong() * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                breakTimeRemaining = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                isOnBreak = false
                breakTimeRemaining = breakDuration * 60

                // Play sound and show notification for break timer end
                playTimerEndSound(context)
                showTimerEndNotification(context)

                // Reset the main timer but do not start it automatically
                timeRemaining = focusDuration * 60
                isFinished = false
                isRunning = false
            }
        }
        breakTimer.value?.start()
    }

    // Local function to start the main timer
    fun startMainTimer() {
        mainTimer.value = object : CountDownTimer(timeRemaining.toLong() * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                isFinished = true
                isRunning = false

                // Play sound and show notification for main timer end
                playTimerEndSound(context)
                showTimerEndNotification(context)

                // Start break timer automatically
                isOnBreak = true
                startBreakTimer()
            }
        }
        mainTimer.value?.start()
        isRunning = true
    }

    // Reset timer function
    fun resetTimer() {
        mainTimer.value?.cancel()
        breakTimer.value?.cancel()
        isRunning = false
        isFinished = false
        isOnBreak = false
        timeRemaining = focusDuration * 60
        breakTimeRemaining = breakDuration * 60
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Settings Button
        IconButton(
            onClick = { onSettingsClick() },
            modifier = Modifier.align(Alignment.TopEnd).padding(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Settings",
                modifier = Modifier.size(56.dp).padding(3.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement1.Top,
            modifier = Modifier.fillMaxHeight().padding(top = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // Display the current timer value, depending on whether it's the break or main timer
            Text(
                text = if (isOnBreak) formatTime(breakTimeRemaining) else formatTime(timeRemaining),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                horizontalArrangement = SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth().padding(120.dp)
            ) {
                // Play/Pause Button
                IconButton(onClick = {
                    if (isRunning) {
                        mainTimer.value?.cancel()  // Stop the main timer
                        breakTimer.value?.cancel()  // Stop the break timer
                        isRunning = !isRunning
                    } else {
                        if (isOnBreak) {
                            startBreakTimer()  // Start break timer
                        } else {
                            startMainTimer()  // Start focus timer
                        }
                    }

                }) {
                    Icon(
                        painter = painterResource(id = if (isRunning) R.drawable.pause else R.drawable.play),
                        contentDescription = if (isRunning) "Pause" else "Play",
                        modifier = Modifier.size(50.dp).padding(3.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Reset Button
                IconButton(onClick = { resetTimer() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = "Reset",
                        modifier = Modifier.size(50.dp).padding(3.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            if (isFinished) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Take a break",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // Editable Text Field with Tick Button on the Right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (isEditingTask) {
                    OutlinedTextField(
                        value = taskText,
                        onValueChange = { taskText = it },

                        placeholder = { Text(
                            text = "Type here...",
                            style = MaterialTheme.typography.labelSmall
                        )
                                      },
                        textStyle = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .weight(1f)
                            .border(2.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(12.dp)),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            // Save task and close text field
                            isEditingTask = false
                            Log.d("Task", "Task Saved: $taskText")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_task),
                            contentDescription = "Confirm Task",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    TextButton(
                        onClick = { isEditingTask = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = taskText.ifEmpty { "Add a Task" },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

// Utility function to format time in mm:ss
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.US, "%02d:%02d", minutes, remainingSeconds)
}

@Preview(showBackground = true)
@Composable
fun PreviewMainContentScreen() {
    MainContentScreen(focusDuration = 25, breakDuration = 5, onSettingsClick = {})
}
