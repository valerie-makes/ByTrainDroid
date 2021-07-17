package co.zimly.bytrain.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import co.zimly.bytrain.model.Station

@ExperimentalMaterialApi
@Composable
fun JourneyCard(from: Station, to: Station, modifier: Modifier = Modifier) {
    Card(onClick = {}, modifier.fillMaxWidth(), role = Role.Button) {
        Row(Modifier.padding(16.dp)) {
            Column {
                Text(from.name)
                Text(to.name)
            }
        }
    }
}
