package com.lynaysereyvath.remindme.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: QuoteRepository) :
    ViewModel() {


    private val _quoteList = mutableStateListOf<QuoteState>()
    val quoteList: List<QuoteState> = _quoteList

    fun getQuoteList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAll().data!!.toQuoteListState()
            _quoteList.addAll(result.quotes)
        }
    }

    private val _searchKeyWord = MutableStateFlow<String>("")
    val searchKeyword = _searchKeyWord.asStateFlow()
    fun setSearchKeyWord(word: String) {
        _searchKeyWord.tryEmit(word)
    }

    var isItemsSelected by mutableStateOf(false)

    val toggleSelection: (id: Int, isSelected: Boolean) -> Unit = { id, isSelected ->
        Log.i(this.javaClass.name, "toggleSelection: $id")
        //To Do something with the id later

        val item = _quoteList.find { it.quote.id == id }
        val index = _quoteList.indexOf(item)
        _quoteList[index] = item!!.copy(isSelected = isSelected)

        val hasItemSelected = _quoteList.find { it.isSelected } != null
        if (hasItemSelected != isItemsSelected) {
            isItemsSelected = hasItemSelected
        }
    }
;
    val isSelectableOnClick: () -> Boolean = { isItemsSelected }

    val cancelAllSelections: () -> Unit =  {
        for (i in 0 until _quoteList.size) {
            val item = QuoteState(_quoteList[i].quote, false)
            _quoteList[i] = item
            Log.i(this.javaClass.name, "cancelAllSelections: ${_quoteList[i].isSelected}")

        }
        isItemsSelected = false
    }

}