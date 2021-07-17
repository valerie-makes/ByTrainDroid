package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapCalls
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import co.zimly.bytrain.R
import co.zimly.bytrain.model.Station

@ExperimentalMaterialApi
@Composable
fun JourneyCard(from: Station, to: Station, modifier: Modifier = Modifier) {
    Card(onClick = {}, modifier.fillMaxWidth(), role = Role.Button) {
        Row(
            Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.SwapCalls,
                    contentDescription = stringResource(R.string.swap_stations),
                )
            }
            Column {
                Text(from.name)
                Row {
                    Text(stringResource(R.string.to) + " ")
                    Text(to.name)
                }
            }
            Spacer(Modifier.weight(1f))
            NavigationHint()
        }
    }
}
