package com.example.appdicodingevent.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdicodingevent.data.EventRepository
import com.example.appdicodingevent.data.Result
import com.example.appdicodingevent.data.remote.response.ListEventsItem

class SearchViewModel(private val eventRepository: EventRepository): ViewModel() {
    private val _searchEvent = MediatorLiveData<List<ListEventsItem>>()
    val searchEvent: LiveData<List<ListEventsItem>> get() = _searchEvent
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    companion object {
        private const val TAG = "SearchViewModel"
    }

    fun searchEvent(q: String) {
        _isLoading.value = true

        val result = eventRepository.searchEvent(q)

        _searchEvent.addSource(result) { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true
                is Result.Success -> {
                    _isLoading.value = false
                    _searchEvent.value = result.data
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.error
                    Log.d(TAG, "Error fetching events: ${result.error}")
                }
            }
        }
    }
}