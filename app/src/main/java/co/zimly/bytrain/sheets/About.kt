package co.zimly.bytrain.sheets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import co.zimly.bytrain.R
import co.zimly.bytrain.SheetController

@Composable
fun About(sheetController: SheetController) {
    TopAppBar(
        title = { Text(stringResource(R.string.about)) },
        actions = {
            IconButton(onClick = { sheetController.collapse() }) {
                Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.done))
            }
        }
    )
}
