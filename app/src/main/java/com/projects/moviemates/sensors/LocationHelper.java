package com.projects.moviemates.sensors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationHelper {

    private static final String TAG = "LocationHelper";
    public static final String DEFAULT_REGION = "US"; // Define a constant for the default region

    // CHANGED: The listener now includes an error callback for better communication.
    public interface LocationListener {
        void onRegionDetermined(String region);
        void onLocationError(String errorMessage);
    }

    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;
    private final Geocoder geocoder;

    public LocationHelper(Context context) {
        this.context = context.getApplicationContext(); // Use application context to avoid memory leaks
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.context);
        this.geocoder = new Geocoder(this.context, Locale.getDefault());
    }

    public void requestLocation(LocationListener listener) {
        // FIXED: Proper permission check. If permission is not granted, fail immediately.
        // The Activity/Fragment is responsible for *asking* for the permission.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listener.onLocationError("Location permission not granted.");
            return;
        }

        // Use the modern `getCurrentLocation` API which is better for a one-time location request.
        // It actively requests a fresh location, unlike `getLastLocation`.
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cancellationTokenSource.getToken())
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        // We have a location, now geocode it on a background thread.
                        geocodeLocation(location, listener);
                    } else {
                        // This can happen if location is turned off on the device.
                        listener.onLocationError("Failed to get location. It might be turned off.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to get current location", e);
                    listener.onLocationError("Error getting location: " + e.getMessage());
                });
    }

    // NEW METHOD: Geocodes the location safely on a background thread.
    private void geocodeLocation(@NonNull Location location, @NonNull LocationListener listener) {
        // For Android 13 (API 33) and above, Geocoder has a modern async API.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1, addresses -> {
                    if (addresses != null && !addresses.isEmpty()) {
                        String countryCode = addresses.get(0).getCountryCode();
                        listener.onRegionDetermined(countryCode != null ? countryCode : DEFAULT_REGION);
                    } else {
                        listener.onRegionDetermined(DEFAULT_REGION);
                    }
                });
            } catch (IllegalArgumentException e) {
                // This can happen with invalid lat/lon values
                Log.e(TAG, "Geocoder failed with invalid arguments.", e);
                listener.onLocationError("Invalid coordinates for geocoding.");
            }
        } else {
            // For older versions, we must run the blocking call on a background thread.
            // Use an ExecutorService for background work.
            ExecutorService executor = Executors.newSingleThreadExecutor();
            // Use a Handler to post the result back to the main thread.
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    handler.post(() -> {
                        if (addresses != null && !addresses.isEmpty()) {
                            String countryCode = addresses.get(0).getCountryCode();
                            listener.onRegionDetermined(countryCode != null ? countryCode : DEFAULT_REGION);
                        } else {
                            listener.onRegionDetermined(DEFAULT_REGION);
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, "Geocoder failed with IOException.", e);
                    handler.post(() -> listener.onRegionDetermined(DEFAULT_REGION)); // Fallback on geocoder error
                }
            });
        }
    }
}