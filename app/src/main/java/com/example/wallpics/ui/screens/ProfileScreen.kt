package com.example.wallpics.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpics.models.WallpaperViewModel
import com.example.wallpics.ui.Route
import com.example.wallpics.ui.components.MenuItem
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.LightColorScheme


@Composable
fun ProfileScreen(
    wallpaperViewModel: WallpaperViewModel = viewModel(),
    navController: NavController
) {
    val isDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PikMe",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background,
                    fontSize = 32.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Contenido",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background,
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opción "Subidas"
            MenuItem(
                iconRes =  Icons.Rounded.Upload,
                title = "Subidas",
                subtitle = "Subir contenido y ver tus subidas",
                isDarkTheme,
                onNavigate = {
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opción "Descargas"
            MenuItem(
                iconRes = Icons.Rounded.Download,
                title = "Descargas",
                subtitle = "",
                isDarkTheme,
                onNavigate = {
                    navController.navigate(Route.Download)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opción "Cambiar de tema"
            MenuItem(
                iconRes = Icons.Rounded.DarkMode,
                title = "Cambiar de tema",
                subtitle = "",
                isDarkTheme,
                onNavigate = {
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Opción "Iniciar sesión"
            MenuItem(
                iconRes = Icons.Rounded.Person,
                title = "Cerrar sesión",
                subtitle = "",
                isDarkTheme,
                onNavigate = {
                }
            )
        }
    }
}