package com.example.flightplanner.Domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Location")
data class LocationModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("Id")
    var id: Int = 0,
    @SerializedName("Name")
    var Name: String = ""
)