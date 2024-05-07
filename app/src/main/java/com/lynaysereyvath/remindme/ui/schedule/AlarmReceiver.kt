package com.lynaysereyvath.remindme.ui.schedule

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: QuoteRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

        } else {
            GlobalScope.launch {
                try {
                    val count = repository.getCount()
                    val random = (0..<count).random()
                    val item = repository.selectById(random)

                    val builder = context?.let {
                        NotificationCompat.Builder(it, "quote")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(item.author)
                            .setContentText(item.message)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                    }
                    with(context?.let { NotificationManagerCompat.from(it) }) {
                        if (context?.let {
                                ActivityCompat.checkSelfPermission(
                                    it,
                                    Manifest.permission.POST_NOTIFICATIONS
                                )
                            } != PackageManager.PERMISSION_GRANTED

                        ) {
                            return@with
                        }

                        if (builder != null) {
                            this?.notify(0, builder.build())
                        }
                    }
                } catch (e: Exception) {

                }
            }

        }
    }
}