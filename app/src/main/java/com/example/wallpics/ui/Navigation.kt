package com.example.wallpics.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wallpics.R
import com.example.wallpics.models.WallpaperModel
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.IconoElegidoFondoDark
import com.example.wallpics.ui.theme.IconosDark
import com.example.wallpics.ui.theme.LightColorScheme
import kotlinx.serialization.Serializable


@Serializable
sealed class Route {
    @Serializable
    object Home : Route()
    @Serializable
    object Favorites : Route()
    @Serializable
    object Profile : Route()
    @Serializable
    object WallpaperView : Route()
    @Serializable
    object Search: Route()
}


data class TopLevelRoute(
    val name: String,
    val route: Route,
    val icon: ImageVector,
)

val topLevelRoutes = listOf(
    TopLevelRoute("Ultimos Añadidos", Route.Home, Icons.Rounded.Home),
    TopLevelRoute("Favorites", Route.Favorites, Icons.Rounded.Favorite),
    TopLevelRoute("Profile", Route.Profile, Icons.Rounded.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    viewModel: WallpicsViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val route = topLevelRoutes.find { it.route == viewModel.currentRoute }
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp,
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Return to previous screen"
                )
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Route.Search) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search a wallpaper"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
fun BottomNavigation(
    navController: NavController,
    viewModel: WallpicsViewModel
) {
    val isDarkTheme = isSystemInDarkTheme()
    val currentScreen = viewModel.currentRoute

    NavigationBar(
        tonalElevation = 16.dp,
        containerColor = if (isDarkTheme) DarkColorScheme.background else LightColorScheme.background
    ) {
        topLevelRoutes.forEachIndexed { index, item ->
            val isSelected = currentScreen == item.route
            NavigationBarItem(
                selected = false,
                onClick = {
                    viewModel.setNewRoute(item.route)
                    navController.navigate(item.route)
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                color = if (isSelected) {
                                    if (isDarkTheme) IconoElegidoFondoDark else LightColorScheme.secondary
                                } else {
                                    if (isDarkTheme) DarkColorScheme.primary else DarkColorScheme.background
                                },
                                shape = CircleShape
                            )
                            .padding(12.dp) // Padding inside the circle
                    ) {
                        Icon(
                            imageVector = item.icon,
                            tint = if (isSelected) {
                                if (isDarkTheme) Color.White else IconosDark
                            } else {
                                if (isDarkTheme) IconosDark else Color.White
                            },
                            contentDescription = item.name
                        )
                    }
                }
            )
        }
    }
}