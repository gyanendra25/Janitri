package com.example.janitri.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.janitri.MainActivity
import com.example.janitri.R

class NotificationHelper(private val context: Context) {

    companion object {
        const val VITALS_CHANNEL_ID = "vitals_reminders"
        const val VITALS_NOTIFICATION_ID = 1001
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                VITALS_CHANNEL_ID,
                "Vitals Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminders to log pregnancy vitals"
                enableVibration(true)
                setShowBadge(true)

            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

    }

    fun showVitalsReminder() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_add_vitals", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, VITALS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Time to log your vitals")
            .setContentText("Stay on top of your health. Please update your vitals now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0,500,200,500))
            .build()

        try {
            NotificationManagerCompat.from(context).notify(VITALS_NOTIFICATION_ID, notification)
        } catch (e: SecurityException){
            e.printStackTrace()
        }


    }
}