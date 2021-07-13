package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.zimly.bytrain.theme.InterFontFamily

@Composable
fun FormSectionHeader(text: String) {
    Text(
        text.uppercase(),
        Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .semantics {
                heading()
                contentDescription = text
            },
        style = TextStyle(
            fontFamily = InterFontFamily,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface,
        ),
    )
}
