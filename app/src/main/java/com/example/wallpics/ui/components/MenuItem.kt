package com.example.wallpics.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.IconoElegidoFondoDark
import com.example.wallpics.ui.theme.IconosDark
import com.example.wallpics.ui.theme.LightColorScheme

@Composable
fun MenuItem(
    iconRes: ImageVector,
    title: String,
    subtitle: String,
    onNavigate: () -> Unit,
    fabBackgroundColor: Color = if (isSystemInDarkTheme()) Color.White else IconosDark,
    itemContentColor: Color = if (isSystemInDarkTheme())Color.White else IconosDark
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onNavigate() }) {
            Icon(
                iconRes,
                contentDescription = null,
                tint = fabBackgroundColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = itemContentColor,
                    fontSize = 18.sp
                )
            )
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = itemContentColor,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}