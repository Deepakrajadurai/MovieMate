package com.projects.moviemates;

import android.content.Intent;
import android.os.Bundle;

// These are the imports needed for the project structure and common Android classes
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// This is the import for the Data Binding class generated from your XML
import com.projects.moviemates.databinding.ActivityMainBinding;

// This is the import for your navigation target
import com.projects.moviemates.ui.GenreSearchActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inflate the layout using Data Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Apply window insets to the root view
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up click listener for the button to navigate directly
        binding.googleSignInButton.setOnClickListener(v -> {
            navigateToGenreSearch();
        });
    }

    private void navigateToGenreSearch() {
        Intent intent = new Intent(MainActivity.this, GenreSearchActivity.class);
        startActivity(intent);
        finish(); // finish() prevents the user from coming back to this screen
    }
}