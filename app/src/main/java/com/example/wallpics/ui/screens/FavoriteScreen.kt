import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpics.data.FavoritesDao
import com.example.wallpics.models.FavoritesViewModel
import com.example.wallpics.models.FavoritesViewModelFactory
import com.example.wallpics.models.WallpaperEntity
import com.example.wallpics.models.toModel
import com.example.wallpics.ui.components.WallpaperItem

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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
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