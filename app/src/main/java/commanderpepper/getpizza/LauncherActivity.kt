package commanderpepper.getpizza

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
                        PizzaMapScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
                    }
                }
                composable<FavoritesDestination> {

                }
                composable<PermissionsDestination> {

                }
            }
        }
    }
}