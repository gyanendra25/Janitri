package com.example.janitri.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class VitalsReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val notificationHelper = NotificationHelper(applicationContext)
            notificationHelper.showVitalsReminder()
            Result.success()
        } catch (e: Exception){
            e.printStackTrace()
            Result.failure()
        }
    }
}