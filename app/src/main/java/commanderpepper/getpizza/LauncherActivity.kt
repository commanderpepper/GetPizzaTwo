package commanderpepper.getpizza

import android.R.attr
import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.permissions.PermissionsScreen
import commanderpepper.getpizza.map.PizzaMapScreen
import commanderpepper.getpizza.model.feature.map.PizzaMarkerUIState
import commanderpepper.getpizza.model.ui.FavoritesDestination
import commanderpepper.getpizza.model.ui.MapDestination
import commanderpepper.getpizza.model.ui.PermissionsDestination


class LauncherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(title = { Text("Get Pizza") }, actions = {
                    IconButton(onClick = {
                        navController.navigate(FavoritesDestination) {
                            launchSingleTop = true
                        }
                    }) {
                        Image(
                            painter = painterResource(
                                commanderpepper.getpizza.map.R.drawable.ic_favorite
                            ), contentDescription = "Favorites"
                        )
                    }
                })
            }) { innerPadding ->
                NavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = PermissionsDestination
                ) {
                    composable<MapDestination> {
                        PizzaMapScreen(
                            modifier = Modifier
                                .fillMaxSize(),
                            onMapIconClick = { pizzaMarkerUIState ->
                                val mapIntent = createMapIntent(pizzaMarkerUIState)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            },
                            onSearchClick = { searchTerm ->
                                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                                    putExtra(SearchManager.QUERY, searchTerm)
                                }
                                startActivity(intent)
                            })

                    }
                    composable<FavoritesDestination> {

                    }
                    composable<PermissionsDestination> {
                        PermissionsScreen(
                            onDismiss = { lat, lng ->
                                val mapDestination = MapDestination(lat, lng, false)
                                navController.navigate(mapDestination) {
                                    launchSingleTop = true
                                }
                            },
                            onPermissionGranted = { lat, lng ->
                                val mapDestination = MapDestination(lat, lng, true)
                                navController.navigate(mapDestination) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun createMapIntent(pizzaMarkerUIState: PizzaMarkerUIState): Intent {
        val format =
            pizzaMarkerUIState.address?.let { "geo:0,0?q=" + it + "(" + attr.label + ")" }
                ?: ("geo:0,0?q=" + pizzaMarkerUIState.lat.toString() + "," + pizzaMarkerUIState.lng.toString() + "(" + attr.label + ")")
        val gmmIntentUri = Uri.parse(format)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        return mapIntent
    }
}