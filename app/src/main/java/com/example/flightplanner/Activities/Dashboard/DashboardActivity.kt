package com.example.flightplanner.Activities.Dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightplanner.Activities.SearchResult.SearchResultActivity
import com.example.flightplanner.Activities.Splash.GradientButton
import com.example.flightplanner.R
import com.example.flightplanner.ViewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }

    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val locations by viewModel.locations.collectAsState()
    val showLocationLoading = remember(locations) {
        mutableStateOf(locations.isEmpty())
    }
    var from: String=""
    var to:String=""
    var classes:String=""
    var adultPassenger:String=""
    var childPassenger:String=""
    val context = LocalContext.current

//    StatusTopBarColor()

    val didRun = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!didRun.value) {
            viewModel.loadLocationsAndCache()
            if (locations.isEmpty())
                viewModel.getLocationsFromDB()
            didRun.value = true
        }
    }

    Scaffold(
        bottomBar = {MyBottomBar()},
//        modifier = Modifier
//            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.darkPurple2))
                .padding(paddingValues = paddingValues)

        ) {
            item{TopBar()}
            item {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .background(
                            colorResource(R.color.darkPurple),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp)
                ) {

                    YellowTitle("From")
                    val locationNames: List<String> = locations.map{it!!.Name}


                    DropDownList(
                        items=locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint="Select origin",
                        showLocationLoading=showLocationLoading.value
                    ) {
                        selectedItem ->
                        from = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    YellowTitle("To")

                    DropDownList(
                        items=locationNames,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint="Select destination",
                        showLocationLoading=showLocationLoading.value
                    ) {
                            selectedItem ->
                        to = selectedItem
                    }

                    // passenger counter
                    Spacer(modifier = Modifier.height(16.dp))
                    YellowTitle("Passengers")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        PassengerCounter(
                          title = "Adult",
                            modifier = Modifier.weight(1f),
                            onItemSelected = {adultPassenger=it}
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        PassengerCounter(
                            title = "Child",
                            modifier = Modifier.weight(1f),
                            onItemSelected = {childPassenger=it}
                        )
                    }

                    // calendar
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        YellowTitle("Departure date", Modifier.weight(1f))
                        Spacer(modifier = Modifier.height(16.dp))
                        YellowTitle("Return date", Modifier.weight(1f))
                    }
                    DatePickerScreen(Modifier.weight(1f))

                    Spacer(modifier = Modifier.height(16.dp))
                    // class selection
                    YellowTitle("Class")
                    val classItems = listOf("Business class", "First class", "Economy class")

                    DropDownList(
                        items=classItems,
                        loadingIcon = painterResource(R.drawable.seat_black_ic),
                        hint="Select class",
                        showLocationLoading=showLocationLoading.value
                    ) {
                            selectedItem ->
                        classes = selectedItem
                    }

                    // Search Button
                    Spacer(modifier = Modifier.height(16.dp))
                    GradientButton(
                        onClick = {
                            val intent = Intent(context, SearchResultActivity::class.java).apply {
                                putExtra("from", from)
                                putExtra("to", to)
                                putExtra("numPassenger", adultPassenger+childPassenger)
                            }
                            startActivity(context, intent, null)
                        },
                        text = "Search",
                    )

                }
            }
        }
    }

}

@Composable
fun YellowTitle(text: String, modifier: Modifier= Modifier) {
    Text(
        text=text,
        fontWeight = FontWeight.SemiBold,
        color=colorResource(R.color.orange),
        modifier=modifier
    )
}