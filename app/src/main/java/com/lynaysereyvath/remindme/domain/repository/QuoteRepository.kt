package com.lynaysereyvath.remindme.domain.repository

import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getAll(): Resource<Flow<List<QuoteEntity>>>

    suspend fun insert(quoteEntity: QuoteEntity)

    suspend fun delete(quoteEntity: QuoteEntity)

    suspend fun update(quoteEntity: QuoteEntity)

    suspend fun deleteAll()

    suspend fun getOne(): QuoteEntity
    suspend fun getCount(): Int
    suspend fun selectById(id: Int): QuoteEntity
}