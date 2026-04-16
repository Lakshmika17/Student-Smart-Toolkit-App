package com.example.taskify

import android.app.*
import android.content.*
import android.os.Build

class NotificationHelper {

    fun scheduleNotification(context: Context, timeInMillis: Long, task: String) {

        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("task", task)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }
}