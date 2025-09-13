// File: app/src/main/java/com/projects/moviemates/ui/GenreSearchActivity.java
package com.projects.moviemates.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.projects.moviemates.databinding.ActivityGenreSearchBinding;
import com.projects.moviemates.sensors.LocationHelper;
import com.projects.moviemates.ui.adapters.MovieAdapter;
import com.projects.moviemates.viewmodel.MovieViewModel;

public class GenreSearchActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ActivityGenreSearchBinding binding;
    private MovieViewModel movieViewModel;
    private MovieAdapter movieAdapter;
    private LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenreSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        locationHelper = new LocationHelper(this);

        setupRecyclerView();
        checkLocationPermissionAndFetchMovies();
    }

    private void setupRecyclerView() {
        movieAdapter = new MovieAdapter(this);
        binding.movieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.movieRecyclerView.setAdapter(movieAdapter);
    }

    private void checkLocationPermissionAndFetchMovies() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchMoviesBasedOnLocation();
        }
    }

    private void fetchMoviesBasedOnLocation() {
        binding.progressBar.setVisibility(View.VISIBLE);

        locationHelper.requestLocation(new LocationHelper.LocationListener() {
            // CORRECTED: The method name is onRegionDetermined, not onRegionFound, as per the error message.
            @Override
            public void onRegionDetermined(String region) {
                runOnUiThread(() -> {
                    binding.trendingTitle.setText("Trending in " + region);
                    movieViewModel.getPopularMovies(region).observe(GenreSearchActivity.this, movies -> {
                        binding.progressBar.setVisibility(View.GONE);
                        if (movies != null) {
                            movieAdapter.setMovieList(movies);
                        } else {
                            Toast.makeText(GenreSearchActivity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }

            // This is the second method that must be implemented.
            // If this name is also wrong, the compiler will give a similar error, and you can correct it.
            @Override
            public void onLocationError(String error) {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    fetchDefaultMovies("Could not get location: " + error);
                });
            }
        });
    }

    private void fetchDefaultMovies(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        binding.trendingTitle.setText("Trending in US");
        binding.progressBar.setVisibility(View.VISIBLE);
        movieViewModel.getPopularMovies("US").observe(GenreSearchActivity.this, movies -> {
            binding.progressBar.setVisibility(View.GONE);
            if (movies != null) {
                movieAdapter.setMovieList(movies);
            } else {
                Toast.makeText(this, "Failed to fetch default movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchMoviesBasedOnLocation();
            } else {
                fetchDefaultMovies("Location permission denied. Showing default results.");
            }
        }
    }
}