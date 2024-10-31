package com.lynaysereyvath.remindme.data.repository

import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.QuoteDao
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import com.lynaysereyvath.remindme.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(private val quoteDao: QuoteDao) : QuoteRepository {
    override fun getAll(): Resource<List<QuoteEntity>> {
        return try {
            Resource.Success(data = quoteDao.selectAll())
        } catch (e: Exception) {
            Resource.Error(message = "", exception = e)
        }
    }

    override suspend fun insert(quoteEntity: QuoteEntity): Resource<Long> {
        return try {
            Resource.Success(quoteDao.insert(quoteEntity))
        } catch (e: Exception) {
            Resource.Error(message = "inserting fail", exception = e)
        }
    }

    override suspend fun delete(quoteEntity: QuoteEntity): Int {
        return quoteDao.delete(quoteEntity)
    }

    override suspend fun update(quoteEntity: QuoteEntity): Int {
        return quoteDao.update(quoteEntity)
    }

    override suspend fun deleteAll() {
        quoteDao.deleteAll()
    }

    override suspend fun getOne(): QuoteEntity {
        return quoteDao.getOne()
    }

    override suspend fun getCount(): Int {
        return quoteDao.getCount()
    }

    override suspend fun selectById(id: Long): QuoteEntity {
        return quoteDao.selectById(id)
    }
}