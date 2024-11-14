package com.example.permissions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun PermissionsScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onDismiss: (Double, Double) -> Unit,
    onPermissionGranted: (Double, Double) -> Unit,
    viewModel: PermissionsScreenViewModel = koinViewModel()){
    val coroutineScope = rememberCoroutineScope()
    PermissionsScreenStateLess(
        modifier = modifier,
        onDismiss = {
            coroutineScope.launch {
                val sl = viewModel.returnLocation(false)
                val lat = sl.latitude
                val lng = sl.longitude
                onDismiss(lat, lng)
            }
        },
        onPermissionGranted = {
            coroutineScope.launch {
                val sl = viewModel.returnLocation(true)
                val lat = sl.latitude
                val lng = sl.longitude
                onPermissionGranted(lat, lng)
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreenStateLess(modifier: Modifier, onDismiss: () -> Unit, onPermissionGranted: () -> Unit) {
    Box(modifier = modifier){
        val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        LaunchedEffect(permissionState.status) {
            Timber.tag("Humza").d("The permission state is ${permissionState.status.isGranted}")
            if(permissionState.status.isGranted){
                //Go to the next screen
                onPermissionGranted()
            }
        }
        if(permissionState.status.isGranted.not()){
            val rationalText = if (permissionState.status.shouldShowRationale) {
                "Getting your location will show pizza joints near you"
            } else {
                "Getting your location will show pizza joints near you or you'll be dropped in New York"
            }
            BasicAlertDialog(onDismissRequest = { onDismiss() }) {
                Card() {
                    Text(modifier = Modifier.padding(8.dp), text = rationalText)
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }
        }
    }
}