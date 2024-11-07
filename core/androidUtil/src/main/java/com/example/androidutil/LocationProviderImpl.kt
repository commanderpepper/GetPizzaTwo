package com.example.androidutil

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import commanderpepper.getpizza.model.util.SimpleLocation
import kotlinx.coroutines.tasks.await

class LocationProviderImpl(private val fusedLocationProviderClient: FusedLocationProviderClient): LocationProvider {
    @SuppressLint("MissingPermission")
    override suspend fun provideLocation(): SimpleLocation {
        return try {
            val data = fusedLocationProviderClient.lastLocation.await()
            if(data != null){
                SimpleLocation(latitude = data.latitude, longitude = data.longitude)
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