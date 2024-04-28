package com.lynaysereyvath.remindme.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.AlarmDao
import com.lynaysereyvath.remindme.domain.repository.QuoteDao

@Database(entities = [QuoteEntity::class, AlarmEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun alarmDao(): AlarmDao
}