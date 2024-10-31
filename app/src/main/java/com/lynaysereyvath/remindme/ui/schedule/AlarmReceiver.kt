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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.data.local.readString
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: QuoteRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

        } else {
            GlobalScope.launch {
                try {
                    val typeToken = object : TypeToken<ArrayList<Long>>() {}.type
                    val savedKeys = context?.readString("ids")
                    val keys = try {
                        Gson().fromJson<ArrayList<Long>>(savedKeys!!.first(), typeToken)
                    } catch (e: Exception) {
                        ArrayList<Long>()
                    }
                    val randomId = keys.random()
                    val item = repository.selectById(randomId)

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