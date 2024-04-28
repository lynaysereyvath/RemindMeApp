package com.lynaysereyvath.remindme.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("select * from tblAlarm order by id asc")
    fun selectAll(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmEntity: AlarmEntity)

    @Delete
    suspend fun delete(alarmEntity: AlarmEntity)

    @Update
    suspend fun update(alarmEntity: AlarmEntity)

    @Query("delete from tblAlarm")
    suspend fun deleteAll()
}