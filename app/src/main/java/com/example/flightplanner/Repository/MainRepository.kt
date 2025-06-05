package com.example.flightplanner.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flightplanner.Domain.FlightModel
import com.example.flightplanner.Domain.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class MainRepository {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    companion object {
        private const val BASE_URL = "https://flightplannermaster-default-rtdb.firebaseio.com/"
    }

    private val client = OkHttpClient()
    private val gson = Gson()

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        val listData = MutableLiveData<MutableList<LocationModel>>()
        val url = "${BASE_URL}Locations.json"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainRepository", "loadLocation() HTTP failure", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { resp ->
                    if (!resp.isSuccessful) {
                        Log.e("MainRepository", "loadLocation() HTTP error: ${resp.code}")
                        return
                    }

                    val bodyString = resp.body?.string()
                    if (bodyString.isNullOrEmpty() || bodyString == "null") {
                        listData.postValue(mutableListOf())
                        return
                    }

                    try {
                        val listType = object : TypeToken<List<LocationModel>>() {}.type
                        val parsedList: List<LocationModel> = gson.fromJson(bodyString, listType)
                        listData.postValue(parsedList.toMutableList())
                    } catch (e: Exception) {
                        Log.e("MainRepository", "loadLocation() JSON parse error", e)
                    }
                }
            }
        })
        return listData
    }



    fun loadFilteredFlights(from: String, to: String): LiveData<MutableList<FlightModel>> {
        val listData = MutableLiveData<MutableList<FlightModel>>()
        val ref = firebaseDatabase.getReference("Flights")
        val query: Query = ref.orderByChild("from").equalTo(from)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<FlightModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(FlightModel::class.java)
                    if (list != null) {
                        if (list.To == to) {
                            lists.add(list)
                        }
                    }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listData
    }

}