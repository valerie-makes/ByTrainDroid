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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.zimly.bytrain.R
import co.zimly.bytrain.model.Station
import co.zimly.bytrain.theme.ByTrainTheme

@ExperimentalMaterialApi
@Composable
fun JourneyCard(from: Station, to: Station, modifier: Modifier = Modifier) {
    Card(onClick = {}, modifier.fillMaxWidth(), role = Role.Button) {
        Row(
            Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.SwapCalls,
                    contentDescription = stringResource(R.string.swap_stations),
                )
            }
            Column {
                StationText(from.name)
                Row {
                    Text(
                        stringResource(R.string.to) + " ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    StationText(to.name)
                }
            }
            Spacer(Modifier.weight(1f))
            NavigationHint()
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun JourneyCardPreview() {
    ByTrainTheme {
        JourneyCard(
            from = Station("WAT", "London Waterloo"),
            to = Station("PAD", "London Paddington"),
        )
    }
}

// TODO: extract for use elsewhere in the app
@Composable
private fun StationText(name: String) {
    Text(
        name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.onBackground,
    )
}
