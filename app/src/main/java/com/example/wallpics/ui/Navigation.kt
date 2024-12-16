package com.example.wallpics.ui

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wallpics.R
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
    object Search : Route()
    @Serializable
    object Login : Route()
    @Serializable
    object Register : Route()
    @Serializable
    object Popular: Route()
    object Download: Route()
}

data class TopLevelRoute(
    val name: String,
    val route: Route,
    val icon: ImageVector,
)

val topLevelRoutes = listOf(
    TopLevelRoute("Ultimos Añadidos", Route.Home, Icons.Rounded.Home),
    TopLevelRoute("Favorites", Route.Favorites, Icons.Rounded.Favorite),
    TopLevelRoute("Profile", Route.Profile, Icons.Rounded.Person),
    TopLevelRoute("Populares", Route.Popular, Icons.Rounded.Star)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    viewModel: WallpicsViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    // Estado para la ruta actual
    val currentRoute = remember { mutableStateOf<String?>(null) }

    // Usamos LaunchedEffect para observar los cambios de la ruta
    LaunchedEffect(navController) {
        // cambios de ruta en el NavController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val route = destination.route?.substringAfterLast(".") // Obtiene el nombre de la clase
            currentRoute.value = route
            Log.d("TopBar", "Ruta actual: ${currentRoute.value}")
        }
    }

    // Verificar si la ruta actual es una de las rutas principales
    val currentRouteObject = getRouteFromString(currentRoute.value)
    val isMainRoute = topLevelRoutes.any { it.route == currentRouteObject }

    // Si currentRoute es null, no mostramos nada o mostramos una vista temporal
    if (currentRoute.value == null) {
        Log.d("TopBar", "Esperando la ruta actual...")
        return //mostrar una vista de espera o hacer algo mientras se carga la ruta
    }

    // Condicional para verificar si la ruta actual es Login o Register
    if (currentRoute.value != Route.Login::class.simpleName && currentRoute.value != Route.Register::class.simpleName) {
        Log.d("TopBar", "Ruta diferente a Login o Register, mostrando TopAppBar completo.")
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
                if (isMainRoute) {

                } else {
                    val context = LocalContext.current
                    IconButton(onClick = {
                        if (!navController.popBackStack()) {
                            (context as? Activity)?.finish()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            },
            actions = {
                if(currentRoute.value == Route.Home::class.simpleName){
                    IconButton(onClick = { navController.navigate(Route.Search) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Busca un wallpaper"
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@Composable
fun BottomNavigation(
    navController: NavController,
    viewModel: WallpicsViewModel
) {
    val isDarkTheme = isSystemInDarkTheme()
    val currentScreen = viewModel.currentRoute

    // Estado para la ruta actual
    val currentRoute = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val route = destination.route?.substringAfterLast(".") // Obtiene el nombre de la clase
            currentRoute.value = route
            Log.d("TopBar", "Ruta actual: ${currentRoute.value}")
        }
    }

    if (currentRoute.value == null) {
        Log.d("TopBar", "Esperando la ruta actual...")
        return
    }

    if (currentRoute.value != Route.Login::class.simpleName && currentRoute.value != Route.Register::class.simpleName
        && currentRoute.value != Route.WallpaperView::class.simpleName && currentRoute.value != Route.Search::class.simpleName) {
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
                            .padding(12.dp)
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
}

fun getRouteFromString(routeName: String?): Route? {
    return when (routeName) {
        Route.Home::class.simpleName -> Route.Home
        Route.Favorites::class.simpleName -> Route.Favorites
        Route.Profile::class.simpleName -> Route.Profile
        Route.WallpaperView::class.simpleName -> Route.WallpaperView
        Route.Search::class.simpleName -> Route.Search
        Route.Login::class.simpleName -> Route.Login
        Route.Register::class.simpleName -> Route.Register
        else -> null
    }
}
