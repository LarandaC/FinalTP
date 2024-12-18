import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpics.data.FavoritesDao
import com.example.wallpics.models.FavoritesViewModel
import com.example.wallpics.models.FavoritesViewModelFactory
import com.example.wallpics.models.WallpaperEntity
import com.example.wallpics.models.toModel
import com.example.wallpics.ui.components.WallpaperItem
import com.example.wallpics.ui.theme.BarraFondoDark
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.LightColorScheme

@Composable
fun FavoritesScreen(
    favoritesDao: FavoritesDao,
    navController: NavController,
    onWallpaperClick: (WallpaperEntity) -> Unit
) {
    val viewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(favoritesDao)
    )

    // Observar la lista de favoritos desde el ViewModel
    val favorites by viewModel.favorites.observeAsState(emptyList())

    val isDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Card(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .wrapContentSize() // Ajusta el tamaño de la tarjeta al contenido
                .padding(horizontal = 30.dp), // Ajusta el padding según sea necesario
            shape = RoundedCornerShape(50), // Hace que la tarjeta sea ovalada
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkTheme) DarkColorScheme.primary else BarraFondoDark
            ),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Text(
                text = "Favoritos",
                modifier = Modifier
                    .padding(10.dp),
                fontWeight = FontWeight.SemiBold,
                color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

//        Box(
//            modifier = Modifier
//                .padding(10.dp)
//                .clip(RoundedCornerShape(50.dp))
//                .background(if (isDarkTheme) DarkColorScheme.primary else BarraFondoDark) // Color de fondo según el tema
//        ) {
//            Text(
//                text = "Favoritos",
//                modifier = Modifier
//                    .padding(10.dp),
//                fontWeight = FontWeight.SemiBold,
//                color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background, // Color según el tema
//                style = MaterialTheme.typography.bodyMedium,
//            )
//        }

        if (favorites.isEmpty()) {
            Text(
                text = "No se guardaron favoritos",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp,
                modifier = Modifier.fillMaxSize()
            ) {
                items(favorites) { wallpaperEntity ->
                    WallpaperItem(
                        wallpaper = wallpaperEntity.toModel(),
                        isFavorite = true,
                        onClick = { onWallpaperClick(wallpaperEntity) },
                        onDoubleClick = {
                            viewModel.removeFavorite(wallpaperEntity)
                        },
                        onRemoveFavorite = {
                            viewModel.removeFavorite(wallpaperEntity)
                        }
                    )
                }
            }
        }
    }
}