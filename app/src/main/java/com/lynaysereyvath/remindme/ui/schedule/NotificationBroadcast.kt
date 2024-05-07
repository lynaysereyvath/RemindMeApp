package com.lynaysereyvath.remindme.ui.schedule

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import javax.inject.Inject

class NotificationBroadcast : BroadcastReceiver() {

    @Inject
    lateinit var repository: QuoteRepository
    
    override fun onReceive(context: Context, intent: Intent?) {

        val builder = NotificationCompat.Builder(context, "Quote")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED

            ) {
                return@with
            }

            notify(0, builder.build())
        }
    }
}