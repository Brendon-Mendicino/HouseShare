package com.houseshare.domain.navigation

import android.util.Log
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object NavigationSettingsSerializer : Serializer<NavigationSettings> {

    private const val TAG = "NavigationSettingsSerializer"

    override val defaultValue: NavigationSettings
        get() = NavigationSettings()

    override suspend fun readFrom(input: InputStream): NavigationSettings {
        return try {
            Json.decodeFromString(
                deserializer = NavigationSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            Log.e(TAG, "readFrom: ${e.printStackTrace()}", e)
            defaultValue
        }
    }

    override suspend fun writeTo(t: NavigationSettings, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = NavigationSettings.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}