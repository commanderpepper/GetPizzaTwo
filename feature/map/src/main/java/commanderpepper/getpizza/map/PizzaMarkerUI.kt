package commanderpepper.getpizza.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState

@Composable
fun PizzaMarkerUI(pizzaMarkerUIState: PizzaMarkerUIState) {
    var isExpanded : Boolean by remember { mutableStateOf(false) }
    if (isExpanded) {
        Card(modifier = Modifier.padding(4.dp), onClick = { isExpanded = false }) {
            Text(modifier = Modifier.padding(4.dp), text = pizzaMarkerUIState.name)
            if (pizzaMarkerUIState.address.isNullOrEmpty().not()) {
                Text(modifier = Modifier.padding(4.dp), text = pizzaMarkerUIState.address!!)
            }
        }
    } else {
        Row(modifier = Modifier
            .padding(4.dp)
            .clickable { isExpanded = true }
        , verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.padding(4.dp),
                    painter = painterResource(if (pizzaMarkerUIState.isFavorite) commanderpepper.getpizza.androidutil.R.drawable.ic_pizza_favorite else commanderpepper.getpizza.androidutil.R.drawable.ic_pizza_non_favorite),
                    contentDescription = ""
                )
            Text(modifier = Modifier.padding(4.dp), text = pizzaMarkerUIState.name)
        }
    }
}

@Preview
@Composable
fun PizzaMarkerUIPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        PizzaMarkerUI(pizzaMarkerUIState = PizzaMarkerUIStateSample)
        PizzaMarkerUI(pizzaMarkerUIState = PizzaMarkerUIStateSample.copy(isFavorite = true))
    }
}

private val PizzaMarkerUIStateSample = PizzaMarkerUIState(
    id = "1",
    name = "This is a sample",
    "1234 Address",
    lat = 1.0,
    lng = 2.0,
    isFavorite = false
)
