package com.lynaysereyvath.remindme.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.lynaysereyvath.remindme.domain.QuoteEntity

data class QuoteState(
    val quote: QuoteEntity, val isSelected: Boolean = false
)

data class QuoteListState(
    val quotes: SnapshotStateList<QuoteState> = mutableStateListOf<QuoteState>()
)

fun List<QuoteEntity>.toQuoteListState(): QuoteListState {
    return QuoteListState(this.map { QuoteState(it, false) }.toMutableStateList())
}