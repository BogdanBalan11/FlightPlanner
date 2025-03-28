package com.example.flightplanner.ViewModel

import androidx.lifecycle.LiveData
import com.example.flightplanner.Activities.Domain.LocationModel
import com.example.flightplanner.Repository.MainRepository

class MainViewModel {
    private val repository = MainRepository()

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        return repository.loadLocation()
    }
}