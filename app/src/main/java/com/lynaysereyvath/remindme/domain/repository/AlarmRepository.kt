package com.lynaysereyvath.remindme.domain.repository

import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    fun getAll(): Resource<Flow<List<AlarmEntity>>>

    suspend fun insert(alarmEntity: AlarmEntity)

    suspend fun delete(alarmEntity: AlarmEntity): Int

    suspend fun update(alarmEntity: AlarmEntity): Int

    suspend fun deleteAll()
}