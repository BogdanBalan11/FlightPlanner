package com.example.flightplanner.RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightplanner.Domain.LocationModel


@Database(version = 1, entities = [LocationModel::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAllLocations(): LocationModelDAO
}