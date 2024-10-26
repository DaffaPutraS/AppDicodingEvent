package com.example.appdicodingevent.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appdicodingevent.data.EventRepository
import com.example.appdicodingevent.di.Injection

class FavoriteFactory private constructor(private val eventRepository: EventRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteFactory? = null
        fun getInstance(context: Context): FavoriteFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}