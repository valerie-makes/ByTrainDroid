package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormButton(
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    primaryText: String? = null,
    withArrow: Boolean = false,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    TextButton(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Icon(icon)
        PrimaryText(primaryText)

        if (content != null) {
            content()
        } else {
            Spacer(Modifier.weight(1f))
        }

        if (withArrow) {
            NavigationHint()
        }
    }
}

@Composable
fun FormRow(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    primaryText: String? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier
            .semantics(mergeDescendants = true) {}
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Icon(icon)
        PrimaryText(primaryText)
        content()
    }
}

@Composable
private fun Icon(icon: @Composable (() -> Unit)?) {
    if (icon != null) {
        icon()
        Spacer(Modifier.width(16.dp))
    }
}

@Composable
private fun PrimaryText(primaryText: String?) {
    if (primaryText != null) {
        Text(
            primaryText,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        )
    }
}
