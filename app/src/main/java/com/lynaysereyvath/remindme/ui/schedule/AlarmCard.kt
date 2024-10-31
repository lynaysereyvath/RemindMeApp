package com.lynaysereyvath.remindme.ui.schedule

import android.annotation.SuppressLint
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat.TvExtender
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.isEnable
import com.lynaysereyvath.remindme.ui.RemindMeApp
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme
import java.time.format.TextStyle

@SuppressLint("DefaultLocale")
@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
    alarmEntity: AlarmEntity,
    onCheckedChange: (AlarmEntity) -> Unit
) {
    var checkedState by remember {
        mutableStateOf(alarmEntity.isEnable())
    }
    ListItem(
        modifier = modifier.fillMaxWidth(),
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

@Preview
@Composable
fun AlarmCardPreview() {
    RemindMeTheme {
        AlarmCard(alarmEntity = AlarmEntity(0, 7, 0)) { _ -> }
    }
}