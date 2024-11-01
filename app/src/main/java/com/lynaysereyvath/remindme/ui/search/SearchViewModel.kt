package com.lynaysereyvath.remindme.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import com.lynaysereyvath.remindme.ui.home.QuoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: QuoteRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<QuoteState>>(emptyList())
    val searchResults: StateFlow<List<QuoteState>> = _searchResults.asStateFlow()

    val quotesList = mutableStateListOf<QuoteState>()

    private val _searchTerm = MutableStateFlow<String>("")
    val searchTerm = _searchTerm.asStateFlow()
    fun setSearchKeyWord(word: String) {
        _searchTerm.tryEmit(word)
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            if (query.isNotBlank())
                repository.search(query).collect { results ->
                    _searchResults.value = results.map { QuoteState(it, false) }
                }
            else
                _searchResults.value = emptyList()
            quotesList.clear()
            quotesList.addAll(searchResults.value)
        }
    }

    var isItemsSelected by mutableStateOf(false)

    val toggleSelection: (id: Long, isSelected: Boolean) -> Unit = { id, isSelected ->
        Log.i(this.javaClass.name, "toggleSelection: $id")
        //To Do something with the id later


        val item = quotesList.find { it.quote.id == id }
        val index = quotesList.indexOf(item)
        quotesList[index] = item!!.copy(isSelected = isSelected)

        val hasItemSelected = quotesList.find { it.isSelected } != null
        if (hasItemSelected != isItemsSelected) {
            isItemsSelected = hasItemSelected
        }
    }

    val isSelectableOnClick: () -> Boolean = { isItemsSelected }

    val cancelAllSelections: () -> Unit = {
        if (quotesList.size > 0)
            for (i in 0 until quotesList.size) {
                val item = QuoteState(quotesList[i].quote, false)
                quotesList[i] = item
                Log.i(this.javaClass.name, "cancelAllSelections: ${quotesList[i].isSelected}")

            }
        isItemsSelected = false
    }

    fun delete(onDeleted: (ArrayList<Long>) -> Unit) {
        val deletedIds = arrayListOf<Long>()
        for (i in quotesList) {
            if (i.isSelected) {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedIds.add(i.quote.id)
                    repository.delete(i.quote)
                    quotesList.remove(i)
                }
            }
        }
        isItemsSelected = false
        onDeleted(deletedIds)
    }
}