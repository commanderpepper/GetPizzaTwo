package com.example.androidutil

import commanderpepper.getpizza.model.util.SimpleLocation

interface LocationProvider {
    suspend fun provideLocation(): SimpleLocation
}