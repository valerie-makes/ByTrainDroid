package co.zimly.bytrain.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.zimly.bytrain.LocalSheetController
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.TitleText
import co.zimly.bytrain.sheets.About

@Composable
fun Profile(navController: NavController) {
    val sheetController = LocalSheetController.current
    sheetController.BackHandler()

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        TitleText(stringResource(R.string.profile))

        Button(onClick = {
            sheetController.present { sheetController -> About(sheetController) }
        }) {
            Text(stringResource(R.string.about))
        }
    }
}
