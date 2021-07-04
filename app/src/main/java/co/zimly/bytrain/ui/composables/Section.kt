package co.zimly.bytrain.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.zimly.bytrain.ui.theme.InterFontFamily

@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Text(
        title,
        Modifier.semantics { heading() }.padding(vertical = 8.dp),
        style = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        ),
    )
    content()
}
