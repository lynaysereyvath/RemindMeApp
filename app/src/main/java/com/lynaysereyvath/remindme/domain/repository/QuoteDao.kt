package com.lynaysereyvath.remindme.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lynaysereyvath.remindme.domain.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("select * from tblQuote order by id asc")
    fun selectAll(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quoteEntity: QuoteEntity): Long

    @Delete
    suspend fun delete(quoteEntity: QuoteEntity): Int

    @Update
    suspend fun update(quoteEntity: QuoteEntity): Int

    @Query("delete from tblQuote")
    suspend fun deleteAll()

    @Query("select * from tblQuote where id = 1")
    suspend fun getOne(): QuoteEntity

    @Query("select count(*) from tblQuote")
    suspend fun getCount(): Int

    @Query("select * from tblQuote where id = :id")
    suspend fun selectById(id: Long): QuoteEntity

    @Query("select * from tblQuote where message like '%' || :query || '%' or author like '%' || :query || '%' order by id asc")
    suspend fun search(query: String): List<QuoteEntity>

}