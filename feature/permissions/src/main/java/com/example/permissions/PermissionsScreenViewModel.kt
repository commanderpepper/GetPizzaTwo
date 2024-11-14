package com.example.permissions

import androidx.lifecycle.ViewModel
import com.example.androidutil.LocationProvider
import commanderpepper.getpizza.model.util.SimpleLocation

class PermissionsScreenViewModel(private val locationProvider: LocationProvider): ViewModel() {
    suspend fun returnLocation(hasPermission: Boolean): SimpleLocation{
        return if(hasPermission) locationProvider.provideLocation() else SimpleLocation(40.77,-73.97)
    }
}