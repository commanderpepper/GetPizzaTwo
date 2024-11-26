package commanderpepper.getpizza

import android.R.attr
import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.permissions.PermissionsScreen
import commanderpepper.getpizza.map.PizzaMapScreen
import commanderpepper.getpizza.model.ui.FavoritesDestination
import commanderpepper.getpizza.model.ui.MapDestination
import commanderpepper.getpizza.model.ui.PermissionsDestination


class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = PermissionsDestination){
                composable<MapDestination> {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        PizzaMapScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            onMapIconClick = { pizzaMarkerUIState ->
                                val format =
                                    pizzaMarkerUIState.address?.let { "geo:0,0?q=" + it + "(" + attr.label + ")" }
                                        ?: ("geo:0,0?q=" + pizzaMarkerUIState.lat.toString() + "," + pizzaMarkerUIState.lng.toString() + "(" + attr.label + ")")
                                val gmmIntentUri = Uri.parse(format)
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
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
                }
                composable<FavoritesDestination> {

                }
                composable<PermissionsDestination> {
                    PermissionsScreen(
                        onDismiss = { lat, lng ->
                            val mapDestination = MapDestination(lat, lng)
                            navController.navigate(mapDestination)
                        },
                        onPermissionGranted = { lat, lng ->
                            val mapDestination = MapDestination(lat, lng)
                            navController.navigate(mapDestination)
                        }
                    )
                }
            }
        }
    }
}