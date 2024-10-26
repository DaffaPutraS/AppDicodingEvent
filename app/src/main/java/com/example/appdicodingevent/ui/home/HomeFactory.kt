package com.example.appdicodingevent.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdicodingevent.data.EventRepository
import com.example.appdicodingevent.di.Injection

class HomeFactory private constructor(private val eventRepository: EventRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeFactory? = null
        fun getInstance(context: Context): HomeFactory =
            instance ?: synchronized(this) {
                instance ?: HomeFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}