package com.houseshare.domain.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationRepository {

    fun fetchNavigation(): Flow<NavigationSettings>

    suspend fun fetchInitialNavigation(): NavigationSettings

    suspend fun updateDestination(destination: Destination)
}