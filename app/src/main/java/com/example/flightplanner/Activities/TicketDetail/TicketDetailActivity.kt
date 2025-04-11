package com.example.flightplanner.Activities.TicketDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.flightplanner.Activities.Domain.FlightModel
import com.example.flightplanner.Activities.SeatSelect.SeatListScreen
import com.example.flightplanner.Activities.Splash.StatusTopBarColor


class TicketDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var flight = intent.getSerializableExtra("flight") as FlightModel

        setContent {
            StatusTopBarColor()

            TicketDetailScreen(
                flight = flight,
                onBackClick = {finish()},
                onDownloadTicketClick = {

                }
            )
        }
    }
}