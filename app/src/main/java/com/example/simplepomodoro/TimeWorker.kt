package com.example.simplepomodoro

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import com.example.simplepomodoro.R

class TimerWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Simulate a 5-minute break timer (in milliseconds)
        val breakTimeRemaining = 5 * 60 * 1000L

        return try {
            // Run the break timer in the background
            val breakTimer = object : CountDownTimer(breakTimeRemaining, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Update UI or state here (if necessary)
                }

                override fun onFinish() {
                    // Play sound and show notification when the break ends
                    playTimerEndSound(applicationContext)
                    showTimerEndNotification(applicationContext)
                }
            }
            breakTimer.start()

            Result.success()
        } catch (e: Exception) {
            // Log the error and return failure result
            Result.failure()
        }
    }

    private fun playTimerEndSound(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.timer_end_sound)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { it.release() }
    }

    private fun showTimerEndNotification(context: Context) {
        val channelId = "timer_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Timer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is the channel for Pomodoro timer notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Pomodoro Timer")
            .setContentText("Time's up! Your break has ended.")
            .setSmallIcon(R.drawable.timer)  // Replace with your actual icon
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
