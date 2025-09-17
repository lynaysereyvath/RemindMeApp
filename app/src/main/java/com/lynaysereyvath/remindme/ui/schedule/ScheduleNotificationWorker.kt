package com.lynaysereyvath.remindme.ui.schedule

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.data.local.readString
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

@HiltWorker
class ScheduleNotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: QuoteRepository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val ALARM_ID = "alarmId"
        const val HOUR = "hour"
        const val MINUTE = "minute"
    }

    override suspend fun doWork(): Result {
        Log.i("AlarmReceiver", "doWork Started")
        return try {
            val typeToken = object : TypeToken<ArrayList<Long>>() {}.type
            val savedKeys = applicationContext.readString("ids")
            savedKeys.collectLatest { keys ->
                Log.i("AlarmReceiver", "savedKeys: $keys")
                Gson().fromJson<ArrayList<Long>>(savedKeys.first(), typeToken).apply {
                    Log.i("AlarmReceiver", "onReceive: $this")
                    val randomId = this.random()
                    Log.i("AlarmReceiver", "randomId: $randomId")
                    val item = repository.selectById(randomId)

                    showNotification(applicationContext, item.author, item.message)

                    inputData.apply {
                        getInt("alarmId", 0).takeIf { it != 0 }?.apply {
                            scheduleExactAlarm(
                                applicationContext,
                                getInt("hour", 0),
                                getInt("minute", 0),
                                this
                            )
                        }
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.i("AlarmReceiver", "Exception: ${e.message}")
            Result.failure()
        }
    }


    private fun createNotification(): Notification =
        NotificationCompat.Builder(applicationContext, "foreground")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
}
