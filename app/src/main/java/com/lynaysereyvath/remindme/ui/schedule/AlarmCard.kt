package com.lynaysereyvath.remindme.ui.schedule

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat.TvExtender
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.isEnable
import com.lynaysereyvath.remindme.ui.RemindMeApp
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme
import java.time.format.TextStyle

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
    alarmEntity: AlarmEntity,
    onCheckedChange: (AlarmEntity) -> Unit,
    onDeleted: (AlarmEntity) -> Unit
) {
    var checkedState by remember {
        mutableStateOf(alarmEntity.isEnable())
    }
    val interactionSource = remember { MutableInteractionSource() }

    var expanded by remember { mutableStateOf(false) }
    var tapOffset by remember { mutableStateOf(DpOffset.Zero) }
    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = tapOffset
        ) {
            DropdownMenuItem(text = { Text("Delete") }, onClick = {
                expanded = false
                onDeleted(alarmEntity)
            })
        }
        ListItem(
            modifier = modifier
                .fillMaxWidth()
                .combinedClickable(
                    interactionSource = interactionSource,
                    onClick = {},
                    onLongClick = { expanded = true },
                    indication = ripple(bounded = true)
                ),
            headlineContent = {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = String.format(
                            "%02d:%02d",
                            if (alarmEntity.hour <= 12) alarmEntity.hour else alarmEntity.hour - 12,
                            alarmEntity.minute
                        ),
                        fontSize = 28.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .alignByBaseline(),
                        text = if (alarmEntity.hour >= 12) "PM" else "AM",
                        fontSize = 25.sp,
                    )
                }
            },
            leadingContent = {
                if (checkedState)
                    Icon(painterResource(R.drawable.outline_alarm_on_24), "alarm")
                else
                    Icon(painterResource(R.drawable.outline_alarm_off_24), "alarm")
            },
            trailingContent = {
                Switch(
                    modifier = Modifier,
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it
                        alarmEntity.isEnabled = if (it) 1 else 0
                        onCheckedChange(alarmEntity)
                    }
                )
            }
        )
    }
}

@Preview
@Composable
fun AlarmCardPreview() {
    RemindMeTheme {
        AlarmCard(alarmEntity = AlarmEntity(0, 7, 0), onCheckedChange = {}) { _ -> }
    }
}