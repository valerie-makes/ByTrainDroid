package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun NavigationHint() {
    Spacer(Modifier.width(12.dp))
    Icon(
        Icons.Filled.ArrowForwardIos,
        contentDescription = null,
        Modifier
            .size(16.dp)
            .alpha(ContentAlpha.medium),
    )
}
