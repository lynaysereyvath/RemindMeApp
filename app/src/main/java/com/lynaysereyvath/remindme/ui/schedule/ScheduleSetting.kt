package com.lynaysereyvath.remindme.ui.schedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.isEnable
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme
import com.lynaysereyvath.remindme.ui.theme.Surface
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    openDrawer: () -> Unit
) {
    val mViewModel = hiltViewModel<ScheduleViewModel>()

    val context = LocalContext.current
    val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    LaunchedEffect(key1 = true, block = {
        mViewModel.getAlarmList()
    })
    val alarmEntities by mViewModel.alarmList.collectAsStateWithLifecycle()

    var showTimePicker by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier.background(Surface),
        topBar = {
            TopAppBar(
                title = { Text(text = "Reminders") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Outlined.Menu, "")
                    }
                },
//                actions = {
//                    IconButton(onClick = {
//                        navController.navigateUp()
//                    }) {
//                        Icon(Icons.Outlined.Search, "")
//                    }
//                    IconButton(onClick = {
//                        navController.navigateUp()
//                    }) {
//                        Icon(painterResource(R.drawable.outline_view_agenda_24), "")
//                    }
//                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showTimePicker = true
            }) {
                Icon(painterResource(R.drawable.outline_alarm_add_24), "add alarm")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {

        if (alarmEntities.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    "notification icon",
                    modifier = Modifier
                        .width(80.dp)
                        .aspectRatio(1f),
                    tint = Color.Yellow
                )
                Text("No reminder is set")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(
                    alarmEntities.sortedWith(
                        compareBy(
                            AlarmEntity::hour,
                            AlarmEntity::minute
                        )
                    )
                ) { alarmEntity ->
                    AlarmCard(
                        modifier = Modifier.padding(10.dp),
                        alarmEntity = alarmEntity, onCheckedChange =
                        { item ->
                            mViewModel.updateAlarmEntity(item)

                            val intent = Intent(context, AlarmReceiver::class.java)
                            val pendingIntent = PendingIntent.getBroadcast(
                                context.applicationContext,
                                item.id,
                                intent,
                                PendingIntent.FLAG_IMMUTABLE
                            )
                            if (item.isEnable()) {
                                val cal = Calendar.getInstance()
                                cal.set(Calendar.HOUR_OF_DAY, item.hour)
                                cal.set(Calendar.MINUTE, item.minute)
                                alarmManager.setRepeating(
                                    AlarmManager.RTC_WAKEUP,
                                    cal.timeInMillis,
                                    AlarmManager.INTERVAL_DAY,
                                    pendingIntent
                                )
//                                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis, 120000, pendingIntent)
//                                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
                            } else {
                                alarmManager.cancel(pendingIntent)
                            }
                        }, onDeleted = { entity ->
                            val intent = Intent(context, AlarmReceiver::class.java)
                            val pendingIntent = PendingIntent.getBroadcast(
                                context.applicationContext,
                                entity.id,
                                intent,
                                PendingIntent.FLAG_IMMUTABLE
                            )

                            alarmManager.cancel(pendingIntent)

                            mViewModel.deleteAlarmEntity(entity)
                        })
                }

            }
        }

        val timePickerState = rememberTimePickerState(7, 0, false)
        if (showTimePicker) {
            TimePickerDialog(onDismissRequest = {

            }, confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                        mViewModel.insertAlarmEntity(
                            AlarmEntity(
                                0,
                                timePickerState.hour,
                                timePickerState.minute,
                                0
                            )
                        )
                    }
                ) { Text("OK") }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            }) {
                TimePicker(
                    state = timePickerState
                )
            }
        }
    }
}


@Preview
@Composable
fun SetScheduleScreenPreview() {
    RemindMeTheme {
        SetScheduleScreen(navController = rememberNavController()) {}
    }
}