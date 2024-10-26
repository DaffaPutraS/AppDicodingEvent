package com.example.appdicodingevent.ui.finished

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdicodingevent.data.EventRepository
import com.example.appdicodingevent.di.Injection

class FinishedFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FinishedFactory? = null
        fun getInstance(context: Context): FinishedFactory =
            instance ?: synchronized(this) {
                instance ?: FinishedFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}