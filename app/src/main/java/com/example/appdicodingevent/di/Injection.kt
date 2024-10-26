package com.example.appdicodingevent.di

import android.content.Context
import com.example.appdicodingevent.data.EventRepository
import com.example.appdicodingevent.data.local.room.FavoriteEventDatabase
import com.example.appdicodingevent.data.remote.retrofit.ApiConfig

object Injection  {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteEventDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        return EventRepository.getInstance(apiService, dao)
    }

}