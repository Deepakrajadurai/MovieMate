//// File: app/src/main/java/com/example/moviemate/ui/MainActivity.java
//package com.projects.moviemates.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.projects.moviemates.databinding.ActivityMainBinding;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class MainActivity extends AppCompatActivity {
//
//    private ActivityMainBinding binding;
//    private FirebaseAuth mAuth;
//    // Add GoogleSignInClient and other auth related variables
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            navigateToGenreSearch();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        mAuth = FirebaseAuth.getInstance();
//
//        // TODO: Initialize GoogleSignInClient
//
//        binding.googleSignInButton.setOnClickListener(v -> {
//            // TODO: Implement Google Sign-In logic
//            Toast.makeText(this, "Google Sign-in Clicked", Toast.LENGTH_SHORT).show();
//        });
//
//        binding.facebookSignInButton.setOnClickListener(v -> {
//            // TODO: Implement Facebook Sign-In logic
//            Toast.makeText(this, "Facebook Sign-in Clicked", Toast.LENGTH_SHORT).show();
//        });
//    }
//
//    private void navigateToGenreSearch() {
//        Intent intent = new Intent(MainActivity.this, GenreSearchActivity.class);
//        startActivity(intent);
//        finish(); // Prevent user from coming back to login screen
//    }
//
//    // TODO: Add onActivityResult to handle the sign-in results
//}