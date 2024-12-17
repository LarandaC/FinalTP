package com.example.wallpics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wallpics.models.WallpaperModel
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.LightColorScheme

@Composable
fun DetailImageItem(item: WallpaperModel, IconContentDescription: String, Icon: Int, onActionEvent: () -> Unit) {

    val isDarkTheme = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de placeholder
        Box(
            modifier = Modifier
                .size(85.dp)
                .padding(4.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.thumbs.original),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(item.ratio.toFloat())
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Tamaño: %.2f MB".format(item.fileSize.toInt() / (1024.0 * 1024.0)),
                color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background,
                fontSize = 12.sp
            )
            Text(
                text = "Category: ${item.category}",
                color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background,
                fontSize = 12.sp
            )
        }

        IconButton(onClick = onActionEvent) {
            Icon(
                painter = painterResource(id = Icon),
                contentDescription = IconContentDescription,
                tint = Color(0xFFF2C94C) // Color amarillo
            )
        }
    }
}