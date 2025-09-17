package com.lynaysereyvath.remindme.ui.schedule

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val REPEATING_ALARM_ACTION = "com.lynaysereyvath.remindme.REPEATING_ALARM"
const val CANCEL_ALARM_ACTION = "com.lynaysereyvath.remindme.CANCEL_ALARM"
const val REPEATING_ALARM_REQUEST_CODE = 100
const val REPEATING_INTERVAL_MILLIS = AlarmManager.INTERVAL_DAY

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: QuoteRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            //Cancel all alarm in database

        } else if (intent?.action == REPEATING_ALARM_ACTION) {
            intent.apply {
                getIntExtra("alarmId", 0).also { alarmId ->
                    val workRequest = OneTimeWorkRequestBuilder<ScheduleNotificationWorker>()
                        .setInputData(
                            workDataOf(
                                ScheduleNotificationWorker.ALARM_ID to alarmId,
                                ScheduleNotificationWorker.HOUR to getIntExtra("hour", 0),
                                ScheduleNotificationWorker.MINUTE to getIntExtra("minute", 0)
                            )
                        )
                        .addTag("AlarmTask")
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .setConstraints(
                            Constraints.Builder().setRequiresCharging(false).build()
                        )
                        .build()
                    WorkManager.getInstance(context).enqueueUniqueWork(
                        "alarmId:$alarmId",
                        ExistingWorkPolicy.REPLACE, workRequest
                    )
                }

            }
        }
    }

    companion object {
        const val TAG = "ALARM_MANAGER"
    }

}