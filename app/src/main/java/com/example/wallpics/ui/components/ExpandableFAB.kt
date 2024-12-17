package com.example.wallpics.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class FABItem(
    val icon: ImageVector,
    val text: String,
)

@Composable
fun ExpandableFAB(
    modifier: Modifier = Modifier,
    items: List<FABItem>,
    fabButton: FABItem = FABItem(icon = Icons.Rounded.Add, text = "Expanded"),
    onItemClick: (FABItem) -> Unit
) {

    var buttonClicked by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        // The Expandable Sheet layout
        AnimatedVisibility(
            visible = buttonClicked,
            enter = expandVertically(spring(dampingRatio = 3f)) + fadeIn(),
            exit = shrinkVertically(spring(dampingRatio = 3f)) + fadeOut(
                animationSpec = spring(dampingRatio = 3f)
            )
        ) {
            // display the items
            Column {
                items.forEach { item ->
                    SmallFloatingActionButton(
                        onClick = {
                            onItemClick(item)
                            buttonClicked = false
                        },
                        shape = CircleShape,
                    ) {
                        Icon(
                             item.icon, contentDescription = "refresh"
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                buttonClicked = !buttonClicked
            },
            shape = CircleShape,
        ) {
            Icon(
                imageVector = fabButton.icon, contentDescription = "refresh"
            )
        }

    }


}