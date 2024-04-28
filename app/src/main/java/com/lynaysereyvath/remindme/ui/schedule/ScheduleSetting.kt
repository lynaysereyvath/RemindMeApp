package com.lynaysereyvath.remindme.ui.schedule

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetScheduleScreen(modifier: Modifier = Modifier, navController: NavController) {
    val mViewModel = hiltViewModel<ScheduleViewModel>()

    LaunchedEffect(key1 = true, block = {
        mViewModel.getAlarmList()
    })
    val alarmEntities by mViewModel.alarmList.collectAsStateWithLifecycle()

    var showTimePicker by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Alarm Setting") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showTimePicker = true
            }, shape = CircleShape) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .background(Color(0xFFF6F6F6))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        alarmEntities.sortedWith(
                            compareBy(
                                AlarmEntity::hour,
                                AlarmEntity::minute
                            )
                        )
                    ) { alarmEntity ->
                        AlarmCard(modifier = Modifier.padding(10.dp), alarmEntity = alarmEntity) { item ->
                            mViewModel.updateAlarmEntity(item)
                        }
                    }
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
        SetScheduleScreen(navController = rememberNavController())
    }
}