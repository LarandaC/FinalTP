package com.example.wallpics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.wallpics.ui.WallpicsApp
import com.example.wallpics.ui.theme.WallpicsTheme
import com.example.wallpics.utils.AndroidDownloader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WallpicsTheme {
                WallpicsApp()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WallpicsTheme {
        WallpicsApp()
    }
}