package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun FormButton(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    TextButton(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        content = content,
    )
}

@Composable
fun FormRow(content: @Composable RowScope.() -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        content = content,
    )
}
