package sk.devprog.firmy.data.repository

import android.content.Context
import android.location.Geocoder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.devprog.firmy.data.model.MapCoordinates
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides geocoder for resolving location.
 */
@Singleton
class GeocoderRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @Suppress("DEPRECATION")
    suspend fun resolveAddressCoordinates(address: String): MapCoordinates? =
        withContext(Dispatchers.IO) {
            runCatching {
                Geocoder(context).getFromLocationName(address, 1)?.firstOrNull()?.let {
                    MapCoordinates(it.latitude, it.longitude)
                }
            }.onFailure {
                Timber.e(it, "Could not resolve address coordinates.")
            }.getOrNull()
        }
}
