package com.example.taskify

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var taskInput: EditText
    private lateinit var timeBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var taskContainer: LinearLayout
    private lateinit var db: DatabaseHelper

    private var selectedHour = -1
    private var selectedMinute = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskInput = findViewById(R.id.taskInput)
        timeBtn = findViewById(R.id.timeBtn)
        saveBtn = findViewById(R.id.saveTaskBtn)
        taskContainer = findViewById(R.id.taskContainer)

        db = DatabaseHelper(this)

        // 🔥 LOAD SAVED TASKS
        loadTasks()

        // 🔔 Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        createNotificationChannel()

        // ⏰ Time Picker
        timeBtn.setOnClickListener {
            val picker = TimePickerDialog(
                this,
                { _, hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                    timeBtn.text = "Time: $hour:$minute"
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
            )
            picker.show()
        }

        // 💾 SAVE TASK
        saveBtn.setOnClickListener {

            val task = taskInput.text.toString()

            if (task.isEmpty()) {
                Toast.makeText(this, "Enter task", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedHour == -1) {
                Toast.makeText(this, "Select time first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.insertTask(task) // 🔥 SAVE TO DB
            setAlarm(task)
            addTaskView(task)

            taskInput.text.clear()
        }
    }

    private fun loadTasks() {
        val list = db.getTasks()
        for (task in list) {
            addTaskView(task)
        }
    }

    private fun addTaskView(task: String) {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL

        val textView = TextView(this)
        textView.text = "• $task"
        textView.setTextColor(resources.getColor(android.R.color.white))
        textView.layoutParams =
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        val deleteBtn = Button(this)
        deleteBtn.text = "Delete"

        deleteBtn.setOnClickListener {
            db.deleteTask(task)
            taskContainer.removeView(layout)
        }

        layout.addView(textView)
        layout.addView(deleteBtn)

        taskContainer.addView(layout)
    }

    private fun setAlarm(task: String) {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
        calendar.set(Calendar.MINUTE, selectedMinute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("task", task)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "task_channel",
                "Task Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }
}