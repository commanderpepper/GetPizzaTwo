package commanderpepper.getpizza.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import commanderpepper.getpizza.model.feature.favorites.FavoritesItem
import commanderpepper.getpizza.model.util.SimpleLocation

@Composable
fun FavoritesItemUI(favoritesItem: FavoritesItem, onMapClick: (SimpleLocation) -> Unit, onSearchClick: (String) -> Unit){
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(){
            IconButton(onClick = {onMapClick(favoritesItem.location)}) { Image(painter = painterResource(commanderpepper.getpizza.androidutil.R.drawable.ic_map), contentDescription = "Map") }
            IconButton(onClick = {onSearchClick(favoritesItem.searchTerm)}) { Image(painter = painterResource(commanderpepper.getpizza.androidutil.R.drawable.ic_search), contentDescription = "Search") }
        }
        Text(modifier = Modifier.padding(8.dp), text = favoritesItem.name)
        if(favoritesItem.address.isNullOrEmpty().not()) Text(modifier = Modifier.padding(8.dp), text = favoritesItem.address!!)
        if(favoritesItem.distance.isNullOrEmpty().not()) Text(modifier = Modifier.padding(8.dp), text = favoritesItem.distance!!)
    }
}

@Preview
@Composable
fun FavoritesItemUIPreview(){
    Column {
        FavoritesItemUI(FavoritesItem("id1","Restaurant Name", "Address", "1 mile", SimpleLocation(0.0, 0.0), "Term"), {}, {})
        FavoritesItemUI(FavoritesItem("id2","Restaurant Name", null, null, SimpleLocation(0.0, 0.0), "Term"), {}, {})
    }

}