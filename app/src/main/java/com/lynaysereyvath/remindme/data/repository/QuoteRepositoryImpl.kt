package com.lynaysereyvath.remindme.data.repository

import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.QuoteDao
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import com.lynaysereyvath.remindme.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(private val quoteDao: QuoteDao): QuoteRepository {
    override fun getAll(): Resource<Flow<List<QuoteEntity>>> {
        return try {
            Resource.Success(data = quoteDao.selectAll())
        }
        catch (e: Exception)
        {
            Resource.Error(message = "", exception = e)
        }
    }

    override suspend fun insert(quoteEntity: QuoteEntity) {
        quoteDao.insert(quoteEntity)
    }

    override suspend fun delete(quoteEntity: QuoteEntity) {
        quoteDao.delete(quoteEntity)
    }

    override suspend fun update(quoteEntity: QuoteEntity) {
        quoteDao.update(quoteEntity)
    }

    override suspend fun deleteAll() {
        quoteDao.deleteAll()
    }
}