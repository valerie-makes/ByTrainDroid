package co.zimly.bytrain.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.TitleText

@Composable
fun ByTrainPro() {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TitleText(stringResource(R.string.bytrain_pro))
    }
}