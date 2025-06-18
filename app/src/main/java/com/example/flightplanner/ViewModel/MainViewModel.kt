package com.example.flightplanner.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightplanner.Domain.FlightModel
import com.example.flightplanner.Domain.LocationModel
import com.example.flightplanner.Repository.MainRepository
import com.example.flightplanner.RoomDatabase.LocationModelDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationModelDAO: LocationModelDAO,
): ViewModel() {
    private val repository = MainRepository()

    private val _locations = MutableStateFlow<List<LocationModel?>>(arrayListOf())
    val locations get() = _locations.asStateFlow()

    fun getLocationsFromDB() {
        viewModelScope.launch {
            val local = locationModelDAO.getAllLocations()
            Log.d("MainViewModel", "Loaded ${local.size} locations from Room")
            _locations.value = local
        }
    }

    fun cacheLocations(locations: List<LocationModel>) {
        locations.forEach {
            Log.d("MainViewModel", "Location ID: ${it.id}, name: ${it.Name}")
        }
        viewModelScope.launch {
            Log.d("MainViewModel", "Inserting ${locations.size} locations into Room")
            locationModelDAO.insertAll(locations)
        }
    }

    fun loadLocationsAndCache() {
        val liveData = repository.loadLocations()
        liveData.observeForever { locations ->
            locations?.let {
                Log.d("MainViewModel", "Fetched from Firebase: ${it.size}")
                cacheLocations(it)
                getLocationsFromDB()
              //  _locations.value = it
            }
        }
    }

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        return repository.loadLocations()
    }

    fun loadFilteredFlights(from: String, to: String):
            LiveData<MutableList<FlightModel>> {
        return repository.loadFilteredFlights(from, to)
    }
}