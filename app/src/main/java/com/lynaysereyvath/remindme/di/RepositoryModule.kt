package com.lynaysereyvath.remindme.di

import com.lynaysereyvath.remindme.data.repository.AlarmRepositoryImpl
import com.lynaysereyvath.remindme.data.repository.QuoteRepositoryImpl
import com.lynaysereyvath.remindme.domain.repository.AlarmRepository
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindQuoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository
}