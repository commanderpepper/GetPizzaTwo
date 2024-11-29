package commanderpepper.getpizza.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import commanderpepper.getpizza.model.util.SimpleLocation
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: FavoritesScreenViewModel = koinViewModel(), onMapIconClick: (SimpleLocation) -> Unit, onSearchIconClick: (String) -> Unit){
    val state = viewModel.favoritesScreenState.collectAsState()
    when(state.value){
        FavoritesScreenState.Loading -> {
            Loading(modifier = modifier)
        }
        FavoritesScreenState.NoneFound -> {
            Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Favorite Some Locations")
            }
        }
        is FavoritesScreenState.Success -> {
            LazyColumn(modifier = modifier) {
                items(items = (state.value as FavoritesScreenState.Success).favorites, key = { item -> item.id }){ item ->
                    FavoritesItemUI(
                        favoritesItem = item,
                        onMapClick = {
                            onMapIconClick(it)
                        },
                        onSearchClick = {
                            onSearchIconClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier){
    CircularProgressIndicator(modifier)
}