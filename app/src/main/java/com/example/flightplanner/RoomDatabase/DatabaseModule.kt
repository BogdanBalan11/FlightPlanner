package com.example.flightplanner.RoomDatabase

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "appDatabase.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLocationsDao(appDatabase: AppDatabase): LocationModelDAO =
        appDatabase.getAllLocations()
}