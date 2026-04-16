package com.example.taskify

import android.app.*
import android.content.*
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val task = intent.getStringExtra("task")

        val channelId = "task_channel"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Task Reminder")
            .setContentText(task)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        notificationManager.notify(1, notification)
    }
}