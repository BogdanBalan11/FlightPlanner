package com.example.flightplanner.Activities.Splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.flightplanner.Activities.Dashboard.DashboardActivity
import com.example.flightplanner.Activities.Login.LoginActivity
import com.example.flightplanner.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val savedEmail = prefs.getString("saved_email", null)
        if (!savedEmail.isNullOrEmpty()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
        setContent {
            SplashScreen(onGetStartedClick = {
                startActivity(Intent(this, LoginActivity::class.java))
            })
        }
    }
}

@Composable
@Preview
fun SplashScreen(onGetStartedClick: () -> Unit = {}) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backgroundImg, title, subtitle, startbtn) = createRefs()
        val styledText = buildAnnotatedString {
            append("Discover your \nDream")
            withStyle(style = SpanStyle(color = colorResource(R.color.orange))) {
                append(" Flight")
            }
            append("\nEasily")
        }

        Image(
            painter = painterResource(R.drawable.splash_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(backgroundImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .fillMaxSize()
        )

        Text(
            text = styledText,
            fontSize = 53.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = stringResource(R.string.subtitle_splash),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.orange),
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                }
        )

        Box(modifier = Modifier.constrainAs(startbtn) {
            bottom.linkTo(parent.bottom)
        }) {
            GradientButton(onClick = onGetStartedClick, "Get Started", 32)
        }
    }
}