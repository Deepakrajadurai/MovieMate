// File: app/src/main/java/com/example/moviemate/ui/GenreSearchActivity.java
package com.example.moviemate.ui;

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
import com.example.moviemate.databinding.ActivityGenreSearchBinding;
import com.example.moviemate.sensors.LocationHelper;
import com.example.moviemate.ui.adapters.MovieAdapter;
import com.example.moviemate.viewmodel.MovieViewModel;

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
        locationHelper.requestLocation(region -> {
            binding.trendingTitle.setText("Trending in " + region);
            movieViewModel.getPopularMovies(region).observe(this, movies -> {
                binding.progressBar.setVisibility(View.GONE);
                if (movies != null) {
                    movieAdapter.setMovieList(movies);
                } else {
                    Toast.makeText(this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchMoviesBasedOnLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Showing default results.", Toast.LENGTH_LONG).show();
                // Fetch with a default region like "US"
                fetchMoviesBasedOnLocation();
            }
        }
    }
}