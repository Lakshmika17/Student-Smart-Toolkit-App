package com.example.taskify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val task = intent.getStringExtra("task")

        val builder = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task Reminder 🔔")
            .setContentText(task)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(context).notify(1, builder.build())
    }
}