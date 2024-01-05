package com.example.githubapp.di

import com.example.users_data.di.apiModule
import com.example.users_data.di.repositoriesModule
import com.example.users_domain.di.useCasesModule
import com.example.users_presentation.di.viewModelsModule

val appModules = listOf(
    apiModule,
    repositoriesModule,
    viewModelsModule,
    useCasesModule
)