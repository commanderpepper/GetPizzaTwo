package com.example.androidutil.di

import com.example.androidutil.LocationProvider
import com.example.androidutil.LocationProviderImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidUtilModule = module {
    single { LocationServices.getFusedLocationProviderClient(androidContext()) }
    single<LocationProvider> { LocationProviderImpl(get()) }
}