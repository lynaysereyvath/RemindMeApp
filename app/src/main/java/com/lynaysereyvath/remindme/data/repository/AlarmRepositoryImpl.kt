package com.lynaysereyvath.remindme.data.repository

import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.repository.AlarmDao
import com.lynaysereyvath.remindme.domain.repository.AlarmRepository
import com.lynaysereyvath.remindme.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmRepository {
    override fun getAll(): Resource<Flow<List<AlarmEntity>>> {
        return try {
            Resource.Success(data = alarmDao.selectAll())
        } catch (e: Exception) {
            Resource.Error(message = "", exception = e)
        }
    }

    override suspend fun insert(alarmEntity: AlarmEntity) {
        alarmDao.insert(alarmEntity)
    }

    override suspend fun delete(alarmEntity: AlarmEntity) {
        alarmDao.delete(alarmEntity)
    }

    override suspend fun update(alarmEntity: AlarmEntity) {
        alarmDao.update(alarmEntity)
    }

    override suspend fun deleteAll() {
        alarmDao.deleteAll()
    }
}