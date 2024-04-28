package com.lynaysereyvath.remindme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: QuoteRepository): ViewModel() {

    private val _quoteList = MutableStateFlow(emptyList<QuoteEntity>())
    val quoteList = _quoteList.asStateFlow()

    fun getQuoteList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().data?.collectLatest {
                _quoteList.tryEmit(it)
            }
        }
    }

    private val _searchKeyWord = MutableStateFlow<String>("")
    val searchKeyword = _searchKeyWord.asStateFlow()
    fun setSearchKeyWord(word: String) {
        _searchKeyWord.tryEmit(word)
    }

}