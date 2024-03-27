package com.lynaysereyvath.remindme.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblQuote")
class QuoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "message") val message: String
)
