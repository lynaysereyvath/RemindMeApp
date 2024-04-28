package com.lynaysereyvath.remindme.di

import android.content.Context
import androidx.room.Room
import com.lynaysereyvath.remindme.data.local.AppDatabase
import com.lynaysereyvath.remindme.domain.repository.AlarmDao
import com.lynaysereyvath.remindme.domain.repository.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideQuoteDao(database: AppDatabase): QuoteDao
    {
        return database.quoteDao()
    }

    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase
    {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "remindme_database")
            .build()
    }

    @Provides
    fun provideAlarmDao(database: AppDatabase): AlarmDao {
        return database.alarmDao()
    }
}