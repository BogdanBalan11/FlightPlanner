package com.example.flightplanner.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightplanner.Domain.LocationModel

@Dao
interface LocationModelDAO {
    @Query("Select * from Location")
    suspend fun getAllLocations(): List<LocationModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationModel>)

}