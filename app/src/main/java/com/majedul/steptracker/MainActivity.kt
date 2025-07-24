package com.majedul.steptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.majedul.core.presentation.designsystem.MajedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  enableEdgeToEdge()
        setContent {
            MajedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    val navController = rememberNavController()
                    NavigationRoot(navController = navController)
                }
            }
        }
    }
}

@Preview
@Composable
private fun myPrewiew() {
    MajedTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background

        ) {
          //  Icon(imageVector = AnalyticsIcon, contentDescription = null)
          //  IntroScreen()

        }
    }
}
