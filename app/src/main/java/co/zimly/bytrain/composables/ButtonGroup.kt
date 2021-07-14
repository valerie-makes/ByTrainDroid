package co.zimly.bytrain.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun ButtonGroup(
    selection: Int,
    onSelectionChanged: (Int) -> Unit,
    buttons: List<@Composable RowScope.() -> Unit>
) {
    val startButtonShape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
    val endButtonShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)

    val colors = buttons.indices.map {
        object : ButtonColors {
            @Composable
            override fun backgroundColor(enabled: Boolean): State<Color> {
                return animateColorAsState(
                    if (it == selection) {
                        // Based on `ButtonDefaults.buttonColors()`
                        MaterialTheme.colors.primary
                    } else {
                        // Based on `ButtonDefaults.outlinedButtonColors()`
                        MaterialTheme.colors.surface
                    }
                )
            }

            @Composable
            override fun contentColor(enabled: Boolean): State<Color> {
                return animateColorAsState(
                    if (it == selection) {
                        // Based on `ButtonDefaults.buttonColors()`
                        MaterialTheme.colors.onPrimary
                    } else {
                        // Based on `ButtonDefaults.outlinedButtonColors()`
                        MaterialTheme.colors.primary
                    }
                )
            }
        }
    }

    Row(Modifier.selectableGroup()) {
        buttons.withIndex().forEach { (index, content) ->
            OutlinedButton(
                onClick = { onSelectionChanged(index) },
                Modifier
                    .weight(1f)
                    .semantics { selected = selection == index },
                shape = when (index) {
                    0 -> startButtonShape
                    buttons.size - 1 -> endButtonShape
                    else -> RectangleShape
                },
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                colors = colors[index],
                content = content,
            )
        }
    }
}
