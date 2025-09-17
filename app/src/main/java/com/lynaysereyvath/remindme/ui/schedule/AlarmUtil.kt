package com.lynaysereyvath.remindme.ui.schedule

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lynaysereyvath.remindme.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


fun scheduleExactAlarm(context: Context, hour: Int, minute: Int, alarmId: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    Log.i("ExactAlarmScheduler", "on Start")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            Log.i("ExactAlarmScheduler", "Exit")
            return
        }
    }
    val pendingIntent = createRepeatingAlarmPendingIntent(context, alarmId, hour, minute)

    try {
        val current = Calendar.getInstance()
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            Log.i("ExactAlarmScheduler", "Current: ${current.timeInMillis}")
            if (current.after(this)) {
                add(Calendar.DATE, 1)
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )

            Log.i("ExactAlarmScheduler", "Exact alarm (ID: $alarmId) scheduled for $timeInMillis")
        }
    } catch (e: SecurityException) {
        Log.e("ExactAlarmScheduler", "SecurityException for ID $alarmId: ${e.message}")
    }
}

fun createRepeatingAlarmPendingIntent(
    context: Context,
    alarmId: Int,
    hour: Int,
    minute: Int
): PendingIntent {

    Log.i("ExactAlarmScheduler", "Create Pending Intent")
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        action = REPEATING_ALARM_ACTION
        putExtra("alarmId", alarmId)
        putExtra("hour", hour)
        putExtra("minute", minute)
    }
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }
    Log.i("ExactAlarmScheduler", "hour: $hour minute: $minute")
    return PendingIntent.getBroadcast(
        context,
        alarmId,
        intent,
        flag
    )
}

fun cancelRepeatingAlarm(context: Context, alarmId: Int, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val pendingIntent = createRepeatingAlarmPendingIntent(context, alarmId, hour, minute)
    alarmManager.cancel(pendingIntent)
    pendingIntent.cancel()
    Log.i("ExactAlarmScheduler", "Exact alarm (ID: $alarmId) canceled")
}

fun showNotification(context: Context, title: String, message: String) {
    val builder =
        NotificationCompat.Builder(context, "quote")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            != PackageManager.PERMISSION_GRANTED

        ) {
            return@with
        }

        this.notify((System.currentTimeMillis() % 10000).toInt(), builder.build())
    }
}

fun logToFile(context: Context, tag: String, message: String, throwable: Throwable? = null) {
    try {
        val logFile = File(context.filesDir, "receiver_log.txt")
        if (!logFile.exists()) {
            logFile.createNewFile()
        }
        val timestamp = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS",
            java.util.Locale.getDefault()
        ).format(Date())
        val logLine = "$timestamp $tag: $message\n"
        logFile.appendText(logLine)
        throwable?.let {
            logFile.appendText(Log.getStackTraceString(it) + "\n")
        }
    } catch (e: IOException) {
        Log.e("FileLogger", "Failed to write to log file", e)
    }
}
