package com.lynaysereyvath.remindme.domain.repository

import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getAll(): Resource<List<QuoteEntity>>

    suspend fun insert(quoteEntity: QuoteEntity): Resource<Long>

    suspend fun delete(quoteEntity: QuoteEntity): Int

    suspend fun update(quoteEntity: QuoteEntity): Int

    suspend fun deleteAll()

    suspend fun getOne(): QuoteEntity
    suspend fun getCount(): Int
    suspend fun selectById(id: Long): QuoteEntity
    suspend fun search(query: String): Flow<List<QuoteEntity>>
}