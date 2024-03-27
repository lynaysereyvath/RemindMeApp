package com.lynaysereyvath.remindme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.QuoteDao

@Database(entities = [QuoteEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}