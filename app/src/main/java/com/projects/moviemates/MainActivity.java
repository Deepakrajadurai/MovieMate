package com.projects.moviemates; // Assuming this is the correct package

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projects.moviemates.databinding.ActivityMainBinding; // Make sure this import is correct for your project structure
import com.projects.moviemates.ui.GenreSearchActivity; // Make sure this import is correct

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // Data Binding object
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToGenreSearch();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display

        // Inflate the layout using Data Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Apply window insets to the root view of the binding object
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Your existing logic
        mAuth = FirebaseAuth.getInstance();

        binding.googleSignInButton.setOnClickListener(v -> {
            Toast.makeText(this, "Google Sign-in Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.facebookSignInButton.setOnClickListener(v -> {
            Toast.makeText(this, "Facebook Sign-in Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void navigateToGenreSearch() {
        Intent intent = new Intent(MainActivity.this, GenreSearchActivity.class);
        startActivity(intent);
        finish();
    }
}