package com.example.wallpics.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WallpicsViewModel(): ViewModel() {
    var currentRoute by mutableStateOf(topLevelRoutes[0].route)
        private set


    fun setNewRoute(route: Route) {
        currentRoute = route
    }

}