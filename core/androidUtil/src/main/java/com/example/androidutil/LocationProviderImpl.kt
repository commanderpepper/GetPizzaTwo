package com.example.androidutil

import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import commanderpepper.getpizza.model.util.SimpleLocation
import kotlinx.coroutines.tasks.await

class LocationProviderImpl(private val fusedLocationProviderClient: FusedLocationProviderClient, private val permissionChecker: PermissionChecker): LocationProvider {
    override suspend fun provideLocation(): SimpleLocation {
        return try {
            if(permissionChecker.checkLocationPermission()){
                val request = CurrentLocationRequest.Builder()
                    .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                    .build()
                val data = fusedLocationProviderClient.getCurrentLocation(request, null).await()

                if(data != null){
                    SimpleLocation(latitude = data.latitude, longitude = data.longitude)
                }
                else {
                    SimpleLocation(40.77,-73.97)
                }
            }
            else {
                SimpleLocation(40.77,-73.97)
            }
        }
        catch (e: Exception){
            SimpleLocation(40.77,-73.97)
        }
    }
}