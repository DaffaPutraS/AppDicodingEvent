package com.example.appdicodingevent.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdicodingevent.data.EventRepository
import com.example.appdicodingevent.data.Result
import com.example.appdicodingevent.data.remote.response.Event

class DetailViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _events = MediatorLiveData<Event>()
    val event: LiveData<Event> get() = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun detailEvent(id: String) {
        _isLoading.value = true

        val result = eventRepository.detailEvent(id)

        _events.addSource(result) { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true
                is Result.Success -> {
                    _isLoading.value = false
                    _events.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.error
                    Log.d(TAG, "Error fetching events: ${result.error}")
                }
            }
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}