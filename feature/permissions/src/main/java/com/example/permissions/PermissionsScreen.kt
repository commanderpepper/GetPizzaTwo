package com.example.permissions

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

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
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted){
                onPermissionGranted()
            }
            else {
                onDismiss()
            }
        }
        val permissionState = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
        var showAlertDialog by remember { mutableStateOf(permissionState.status.isGranted.not()) }

        if(showAlertDialog){
            val rationalText = if (permissionState.status.shouldShowRationale) {
                "Getting your location will show pizza joints near you"
            } else {
                "Getting your location will show pizza joints near you (otherwise you'll be dropped in New York)"
            }
            BasicAlertDialog(onDismissRequest = {
                showAlertDialog = false
                onDismiss()
            }) {
                Card() {
                    Text(modifier = Modifier.padding(8.dp), text = rationalText)
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            showAlertDialog = false
                            launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        }) {
                        Text("Request permission")
                    }
                }
            }
        }
        else {
            onPermissionGranted()
        }
    }
}