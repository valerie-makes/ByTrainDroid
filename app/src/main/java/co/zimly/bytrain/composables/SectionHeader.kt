package co.zimly.bytrain.composables

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
import co.zimly.bytrain.theme.InterFontFamily

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
    reduceBottomPadding: Boolean = false
) {
    Text(
        text,
        modifier
            .semantics { heading() }
            .padding(top = 8.dp, bottom = if (reduceBottomPadding) 4.dp else 8.dp),
        style = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        ),
    )
}
