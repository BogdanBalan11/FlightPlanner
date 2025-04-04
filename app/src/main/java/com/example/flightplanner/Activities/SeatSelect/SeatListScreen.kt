package com.example.flightplanner.Activities.SeatSelect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.flightplanner.Activities.Domain.FlightModel
import com.example.flightplanner.R

enum class SeatStatus {
    AVAILABLE,
    SELECTED,
    UNAVAILABLE,
    EMPTY
}

data class Seat(
    var status: SeatStatus,
    var name: String
)

@Composable
fun SeatListScreen(flight: FlightModel,
                   onBackClick:()-> Unit,
                   onConfirm:(FlightModel)-> Unit) {
    val context = LocalContext.current

    val seatList = remember { mutableStateListOf<Seat>() }
    val selectedSeatNames = remember { mutableStateListOf<String>() }

    var seatCount by remember { mutableStateOf(0) }
    var totalPrice by remember { mutableStateOf(0.0) }

    LaunchedEffect(flight) {
        seatList.clear()
        seatList.addAll(generateSeatList(flight))
        seatCount = selectedSeatNames.size
        totalPrice = seatCount * flight.Price
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.darkPurple2))
    ) {
        val (topSection, middleSection, bottomSection) = createRefs()
        TopSection(
            modifier = Modifier
                .constrainAs(topSection) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, onBackClick = onBackClick
        )
    }

}

fun generateSeatList(flight: FlightModel): List<Seat> {
    val seatList = mutableListOf<Seat>()
    val numberSeat = flight.NumberSeat + (flight.NumberSeat / 7) + 1
    val seatAlphabetMap = mapOf(
        0 to "A",
        1 to "B",
        2 to "C",
        4 to "D",
        5 to "E",
        6 to "F",
    )

    var row = 0
    for (i in 0 until numberSeat) {
        if (i % 7 == 0) {
            row++
        }
        if (i % 7 == 3) {
            seatList.add(Seat(SeatStatus.EMPTY, row.toString()))
        } else{
            val seatName = seatAlphabetMap[i%7]+row
            val seatStatus = if (flight.ReservedSeats.contains(seatName)) {
                SeatStatus.UNAVAILABLE
            } else{
                SeatStatus.AVAILABLE
            }
            seatList.add(Seat(seatStatus, seatName))
        }
    }
    return seatList
}