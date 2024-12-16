package com.example.wallpics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpics.models.DetailImageItemModel

@Composable
fun DownloadItemRow(item: DetailImageItemModel, IconContentDescription: String, Icon: Int, onActionEvent: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de placeholder
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.picture_frame),
                contentDescription = "Wallpaper Preview"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tamaño: ${item.fileSize} MB",
                color = Color.LightGray,
                fontSize = 12.sp
            )
            Text(
                text = "Fecha descarga: ${item.date}",
                color = Color.LightGray,
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