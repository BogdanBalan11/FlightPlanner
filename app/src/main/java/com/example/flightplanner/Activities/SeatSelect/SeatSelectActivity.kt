package com.example.flightplanner.Activities.SeatSelect

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flightplanner.Activities.Domain.FlightModel
//import com.example.flightplanner.Activities.Splash.StatusTopBarColor
import com.example.flightplanner.Activities.TicketDetail.TicketDetailActivity
import com.example.flightplanner.R

class SeatSelectActivity : AppCompatActivity() {
    private lateinit var flight: FlightModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flight = intent.getSerializableExtra("flight") as FlightModel

        setContent {
//            StatusTopBarColor()

            SeatListScreen(
                flight = flight,
                onBackClick = {
                    finish()
                }, onConfirm = {
                    val intent = Intent(this, TicketDetailActivity::class.java).apply {
                        putExtra("flight", flight)
                    }
                    startActivity(intent, null)
                }
            )
        }
    }
}