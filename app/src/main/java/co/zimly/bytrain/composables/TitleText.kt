package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.zimly.bytrain.theme.InterFontFamily

@Composable
fun TitleText(text: String) {
    Text(
        text,
        Modifier
            .semantics { heading() }
            .padding(top = 20.dp),
        style = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = MaterialTheme.colors.primary
        ),
    )
}
