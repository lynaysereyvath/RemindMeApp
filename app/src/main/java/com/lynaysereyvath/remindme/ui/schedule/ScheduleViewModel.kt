package com.lynaysereyvath.remindme.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynaysereyvath.remindme.domain.AlarmEntity
import com.lynaysereyvath.remindme.domain.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val repository: AlarmRepository): ViewModel() {

    private val _alarmList = MutableStateFlow(emptyList<AlarmEntity>())
    val alarmList = _alarmList.asStateFlow()
    fun getAlarmList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().data?.collectLatest {
                _alarmList.tryEmit(it)
            }
        }
    }

    fun insertAlarmEntity(alarmEntity: AlarmEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(alarmEntity)
        }
    }

    fun updateAlarmEntity(alarmEntity: AlarmEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(alarmEntity)
        }
    }

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()
    fun setTime(s: String) {
        _time.tryEmit(s)
    }
}