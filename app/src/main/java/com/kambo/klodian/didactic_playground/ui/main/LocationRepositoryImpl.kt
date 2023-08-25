package com.kambo.klodian.didactic_playground.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class LocationRepositoryImpl constructor(
    private val context: Context,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val looper: Looper
) {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    /** suppressed because useless, permissions are checked with [hasGeolocPermissions] method */
    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getLocation(): Result<Location> =
        withContext(coroutineDispatcher) {
            suspendCancellableCoroutine { continuation ->

                if (!hasGeolocPermissions()) {
                    continuation.resume(
                        Result.failure(RuntimeException("Missing permission")),
                        onCancellation = null
                    )
                }

                // this can be configurable, or a policy
                val locationRequest = com.google.android.gms.location.LocationRequest.Builder(5000)
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .build()

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            locationResult.lastLocation?.let { lastLocation ->
                                fusedLocationClient.removeLocationUpdates(this)
                                continuation.resume(
                                    Result.success(lastLocation),
                                    onCancellation = null
                                )
                            }
                        }
                    },
                    looper
                )
            }
        }


    @SuppressLint("MissingPermission")
    fun getLocationFlow(): Flow<Result<Location>> = callbackFlow {

        if (!hasGeolocPermissions()) {
            trySend(Result.failure(RuntimeException("Missing permission")))
        }

        // this can be configurable, or a policy
        val locationRequest = LocationRequest.Builder(5000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                trySend(Result.success(locationResult.lastLocation!!))
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            looper
        )

        awaitClose {
            println("(!) Location flow remove")
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }.flowOn(coroutineDispatcher)

    private fun hasGeolocPermissions(): Boolean =
        !(ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
}