package com.houseshare.data.navigation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import com.houseshare.domain.navigation.Destination
import com.houseshare.domain.navigation.NavigationRepository
import com.houseshare.domain.navigation.NavigationSettings
import com.houseshare.domain.navigation.NavigationSettingsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject


private const val TAG = "NavigationRepositoryImpl"
private const val NAVIGATION_FILE_NAME = "navigation_settings.json"

val Context.dataStore by dataStore(
    fileName = NAVIGATION_FILE_NAME,
    serializer = NavigationSettingsSerializer,
)


class NavigationRepositoryImpl @Inject constructor(
    private val appContext: Application,
) : NavigationRepository {

    override fun fetchNavigation(): Flow<NavigationSettings> =
        appContext.dataStore.data
            .catch {
                if (it is IOException) {
                    Log.e(TAG, "fetchInitialNavigation: Failed to read data.", it)
                    emit(NavigationSettings())
                } else {
                    throw it
                }
            }

    override suspend fun fetchInitialNavigation(): NavigationSettings =
        appContext.dataStore.data
            .catch {
                if (it is IOException) {
                    Log.e(TAG, "fetchInitialNavigation: Failed to read data.", it)
                    emit(NavigationSettings())
                } else {
                    throw it
                }
            }
            .first()

    override suspend fun updateDestination(destination: Destination) {
        appContext.dataStore.updateData {
            it.copy(destination = destination)
        }
    }
}