package com.lynaysereyvath.remindme.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val quoteRepository: QuoteRepository) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    fun setName(s: String) {
        _name.tryEmit(s)
    }

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
    fun setMessage(s: String) {
        _message.tryEmit(s)
    }

    fun insertQuoteEntity(quoteEntity: QuoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (retrievedQuote == null)
                quoteRepository.insert(quoteEntity)
            else
                quoteRepository.update(
                    QuoteEntity(
                        retrievedQuote!!.id,
                        quoteEntity.author,
                        quoteEntity.message
                    )
                )
        }
    }

    private var retrievedQuote: QuoteEntity? = null

    fun getQuote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            retrievedQuote = quoteRepository.selectById(id)
            _name.tryEmit(retrievedQuote?.author ?: "")
            _message.tryEmit(retrievedQuote?.message ?: "")
        }
    }

    fun update(quoteEntity: QuoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.update(quoteEntity)
        }
    }
}