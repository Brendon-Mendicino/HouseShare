package com.houseshare.domain.navigation

interface NavigationRepository {

    suspend fun fetchInitialNavigation(): NavigationSettings

    suspend fun updateDestination(destination: Destination)
}