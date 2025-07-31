package com.example.janitri.notification

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ReminderManager(private val context: Context) {

    companion object {
        private const val VITALS_REMINDER_WORK_NAME = "vitals_Reminder_Work"
        private const val REMINDER_INTERVAL_HOURS = 5L

    }

    fun scheduleVitalsReminders() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .build()

        val reminderWork = PeriodicWorkRequestBuilder<VitalsReminderWorker>(
            REMINDER_INTERVAL_HOURS, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(REMINDER_INTERVAL_HOURS, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            VITALS_REMINDER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            reminderWork
        )
    }

    fun cancelVitalsReminders() {
        WorkManager.getInstance(context).cancelUniqueWork(VITALS_REMINDER_WORK_NAME)
    }

    fun isReminderScheduled(): Boolean {
        val workInfo = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWork(VITALS_REMINDER_WORK_NAME)
        return try {
            val workInfo = workInfo.get()
            workInfo.any { it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING }
        } catch (e: Exception) {
            false
        }
    }
}