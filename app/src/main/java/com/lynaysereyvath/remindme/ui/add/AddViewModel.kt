package com.lynaysereyvath.remindme.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.domain.repository.QuoteRepository
import com.lynaysereyvath.remindme.domain.utils.Resource
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

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun insertQuoteEntity(quoteEntity: QuoteEntity, onInsertSuccess: (id: Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (retrievedQuote == null) {
                when (val result = quoteRepository.insert(quoteEntity)) {
                    is Resource.Success -> {
                        onInsertSuccess(result.data!!)
                    }

                    is Resource.Error -> {
                        Log.d(
                            this@AddViewModel.javaClass.simpleName,
                            result.exception?.message.toString()
                        )
                        _error.tryEmit(result.message)
                    }
                }
            } else
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

    fun getQuote(id: Long) {
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