package com.example.permissions.di

import com.example.permissions.PermissionsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val permissionsModule = module {
    viewModel { PermissionsScreenViewModel(get()) }
}