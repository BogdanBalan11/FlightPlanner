package com.example.flightplanner.ViewModel

import androidx.lifecycle.LiveData
import com.example.flightplanner.Domain.FlightModel
import com.example.flightplanner.Domain.LocationModel
import com.example.flightplanner.Repository.MainRepository

class MainViewModel {
    private val repository = MainRepository()

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        return repository.loadLocations()
    }

    fun loadFilteredFlights(from: String, to: String):
            LiveData<MutableList<FlightModel>> {
        return repository.loadFilteredFlights(from, to)
    }
}