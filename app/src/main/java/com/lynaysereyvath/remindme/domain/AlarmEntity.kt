package com.lynaysereyvath.remindme.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tblAlarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hour: Int,
    val minute: Int,
    var isEnabled: Int = 0
)

fun AlarmEntity.isEnable(): Boolean {
    return isEnabled == 1
}